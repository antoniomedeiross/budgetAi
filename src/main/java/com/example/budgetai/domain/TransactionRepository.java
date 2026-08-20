package com.example.budgetai.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findAllByCategory(Category category);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionEntity t WHERE t.category = :category")
    BigDecimal sumTotalByCategory(@Param("category") Category category);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM TransactionEntity t")
    BigDecimal sumTotalAllCategories();
}
