package com.example.budgetai.infrastructure.persistence.entity;

import com.example.budgetai.domain.Category;
import com.example.budgetai.domain.Transaction;
import com.example.budgetai.domain.TransactionId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.annotation.EnumNaming;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    @Id
    private UUID id;

    private String description;
    private int amount;

    @Enumerated(EnumType.STRING)
    private Category category;

    public static TransactionEntity from(Transaction transaction) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId().uuid());
        entity.setDescription(transaction.getDescription());
        entity.setAmount((int) transaction.getAmount());
        entity.setCategory(transaction.getCategory());
        return entity;
    }

    public Transaction toDomain() {
        return new Transaction(
                new TransactionId(this.id),
                this.description,
                this.amount,
                this.category
        );
    }
}
