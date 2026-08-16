package com.example.budgetai.application;

import com.example.budgetai.application.input.PersistTransactionInput;
import com.example.budgetai.application.output.TransactionOutput;
import com.example.budgetai.domain.Transaction;
import com.example.budgetai.domain.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionOutput execute(PersistTransactionInput input) {
        var transaction = transactionRepository.save(new Transaction(
            input.description(),
            input.amount(),
            input.category()
        ));

        return TransactionOutput.from(transaction);
    }


}
