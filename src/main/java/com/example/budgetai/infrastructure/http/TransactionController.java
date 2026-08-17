package com.example.budgetai.infrastructure.http;

import com.example.budgetai.application.ListTransactionByCategoryUseService;
import com.example.budgetai.application.PersistTransactionUseCase;
import com.example.budgetai.domain.Category;
import com.example.budgetai.infrastructure.http.request.TransactionRequest;
import com.example.budgetai.infrastructure.http.response.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionByCategoryUseService listTransactionByCategoryUseService;

    public TransactionController(PersistTransactionUseCase persistTransactionUseCase,
                                 ListTransactionByCategoryUseService listTransactionByCategoryUseService) {
        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionByCategoryUseService = listTransactionByCategoryUseService;
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

}
