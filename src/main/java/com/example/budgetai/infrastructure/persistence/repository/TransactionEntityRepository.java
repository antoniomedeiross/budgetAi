package com.example.budgetai.infrastructure.persistence.repository;

import com.example.budgetai.domain.Category;
import com.example.budgetai.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface TransactionEntityRepository  extends CrudRepository<TransactionEntity, UUID> {

    List<TransactionEntity> findAllByCategory(Category category);

}
