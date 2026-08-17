package com.example.budgetai.application;

import com.example.budgetai.application.output.TransactionOutput;
import com.example.budgetai.domain.Category;
import com.example.budgetai.domain.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ListTransactionByCategoryUseService {
    private final TransactionRepository transactionRepository;

    public ListTransactionByCategoryUseService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionOutput> execute(Category category) {
        var transactions = transactionRepository.findAllByCategory(category);
        return transactions.stream()
                .map(TransactionOutput::from)
                .toList();
    }
}
