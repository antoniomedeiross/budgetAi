package com.example.budgetai.controller;

import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TextToSpeechController {
    private final TextToSpeechModel textToSpeechModel;

    public TextToSpeechController(TextToSpeechModel textToSpeechModel) {
        this.textToSpeechModel = textToSpeechModel;
    }

    @PostMapping(value = "/sinthesize", produces = "audio/wav")
    public ResponseEntity<ByteArrayResource> sinthesize(@RequestBody SyntesizeRequest request) {
        byte[] audio = textToSpeechModel.call(request.text());
        var resource = new ByteArrayResource(audio);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("output.wav")
                                .build().toString())
                .body(resource);

    }


    record SyntesizeRequest(String text) { }
}
