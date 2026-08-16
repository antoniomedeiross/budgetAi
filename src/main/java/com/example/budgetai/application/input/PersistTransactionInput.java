package com.example.budgetai.application.input;

import com.example.budgetai.domain.Category;

public record PersistTransactionInput(
    String description,
    long amount,
    Category category
) {
}
