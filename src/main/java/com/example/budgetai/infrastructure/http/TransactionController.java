package com.example.budgetai.infrastructure.http;

import com.example.budgetai.application.ListTransactionByCategoryUseService;
import com.example.budgetai.application.PersistTransactionUseCase;
import com.example.budgetai.domain.Category;
import com.example.budgetai.infrastructure.http.request.TransactionRequest;
import com.example.budgetai.infrastructure.http.response.TransactionResponse;

import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final PersistTransactionUseCase persistTransactionUseCase;
    private final TranscriptionModel transcriptionModel;
    private final ListTransactionByCategoryUseService listTransactionByCategoryUseService;
    private final ChatClient chatClient;
    private final TextToSpeechModel textToSpeechModel;


    public TransactionController(PersistTransactionUseCase persistTransactionUseCase,
                                 TranscriptionModel transcriptionModel,
                                 @Value("classpath:prompt/system-message.st") Resource systemPrompt,
                                 ListTransactionByCategoryUseService listTransactionByCategoryUseService,
                                 ChatClient.Builder chatClient, TextToSpeechModel textToSpeechModel) throws IOException {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.transcriptionModel = transcriptionModel;
        this.textToSpeechModel = textToSpeechModel;
        this.listTransactionByCategoryUseService = listTransactionByCategoryUseService;
        this.chatClient = chatClient
                .defaultSystem(systemPrompt.getContentAsString(Charset.defaultCharset()))
                .defaultTools(persistTransactionUseCase, listTransactionByCategoryUseService)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        var transaction = persistTransactionUseCase.execute(request.toInput());
        return TransactionResponse.from(transaction);
    }

    @GetMapping("/{category}")
    @ResponseStatus(HttpStatus.OK)
    public List<TransactionResponse> readTransactions(@PathVariable Category category) {
        return listTransactionByCategoryUseService.execute(category)
                .stream()
                .map(TransactionResponse::from)
                .toList();
    }


    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = "audio/mp3")
    ResponseEntity<Resource> transcribe(@RequestParam("file") MultipartFile file) {
        var userMessage = transcriptionModel.transcribe(file.getResource());

        var result = chatClient.prompt().user(userMessage).call().content();
        System.out.println(result);

        textToSpeechModel.call(result);

        byte[] audio = textToSpeechModel.call(result);
        var resource = new ByteArrayResource(audio);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("output.wav")
                                .build().toString())
                .body(resource);



    }



}
