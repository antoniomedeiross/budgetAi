package com.example.budgetai.infrastructure.http.response;

import com.example.budgetai.application.output.TransactionOutput;

import java.math.BigDecimal;

public record TransactionResponse(String id, String description, Double amount, String category) {
    public static TransactionResponse from(TransactionOutput output) {
        return new TransactionResponse(
            output.id(),
            output.description(),
            output.amount(),
            output.category()
        );
    }
}
