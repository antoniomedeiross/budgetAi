package com.example.budgetai.infrastructure.http.request;

import com.example.budgetai.application.input.PersistTransactionInput;
import com.example.budgetai.domain.Category;

public record TransactionRequest (
        String description,
        long amount,
        Category category ) {

    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(
            description,
            amount,
            category
        );
    }
}
