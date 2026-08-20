package com.example.budgetai.application;

import com.example.budgetai.domain.Category;
import com.example.budgetai.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GetSummaryTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public GetSummaryTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(description = "Calcula e retorna a soma total dos gastos financeiros. Se a categoria for informada, calcula o total apenas daquela categoria. Se a categoria for nula ou não especificada, calcula o total geral de todos os gastos.")
    public SummaryOutput execute(SummaryInput input) {
        if (input.category() != null) {
            BigDecimal total = transactionRepository.sumTotalByCategory(input.category());
            return new SummaryOutput(input.category().name(), total, "Total gasto na categoria " + input.category().name() + ": R$ " + total);
        } else {
            BigDecimal total = transactionRepository.sumTotalAllCategories();
            return new SummaryOutput("TODAS", total, "Total geral de gastos: R$ " + total);
        }
    }

    public record SummaryInput(Category category) {}
    public record SummaryOutput(String category, BigDecimal totalAmount, String message) {}
}
