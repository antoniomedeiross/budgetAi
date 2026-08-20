package com.example.budgetai.infrastructure.persistence.repository;

import com.example.budgetai.domain.Category;
import com.example.budgetai.domain.Transaction;
import com.example.budgetai.domain.TransactionRepository;
import com.example.budgetai.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class JpaTransactionRepository implements TransactionRepository {

    private final TransactionEntityRepository transactionEntityRepository;

    public JpaTransactionRepository(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = TransactionEntity.from(transaction);
        return transactionEntityRepository.save(entity).toDomain();

    }

    @Override
    public List<Transaction> findAllByCategory(Category category) {
        return transactionEntityRepository.findAllByCategory(category)
                .stream()
                .map(TransactionEntity::toDomain)
                .toList();
    }

    @Override
    public BigDecimal sumTotalByCategory(Category category) {
        return null;
    }

    @Override
    public BigDecimal sumTotalAllCategories() {
        return null;
    }
}



