package com.example.budgetai.application.input;

import com.example.budgetai.domain.Category;
import org.springframework.ai.tool.annotation.ToolParam;

public record PersistTransactionInput(
    @ToolParam(description = "Descrição da transação") String description,
    @ToolParam(description = "Valor da transação") long amount,
    @ToolParam(description = "Categoria da transação") Category category
) {
}
