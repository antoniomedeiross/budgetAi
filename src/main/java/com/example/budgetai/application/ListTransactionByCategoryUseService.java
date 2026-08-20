package com.example.budgetai.application;

import com.example.budgetai.application.output.TransactionOutput;
import com.example.budgetai.domain.Category;
import com.example.budgetai.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListTransactionByCategoryUseService {
    private final TransactionRepository transactionRepository;

    public ListTransactionByCategoryUseService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "List-transaction-by-category", description = "Lista todas as transações de uma determinada categoria")
    public List<TransactionOutput> execute(@ToolParam(description = "Categoria de uma transação") Category category) {
        var transactions = transactionRepository.findAllByCategory(category);
        return transactions.stream()
                .map(TransactionOutput::from)
                .toList();
    }
}
