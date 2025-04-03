package org.churchbooks.churchbooks.transactions.entities;

import org.churchbooks.churchbooks.transactions.dtos.CategoryDetails;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public record Category (
        @Id UUID id,
        String name,
        Timestamp createdAt,
        BigDecimal amount,
        UUID budgetId
){
    public Category(String name, BigDecimal amount, UUID budgetId){
        this(null, name, Timestamp.from(Instant.now()), amount, budgetId);
    }

    public Category updateCategory(CategoryDetails categoryDetails){
        return new Category(id, categoryDetails.name(), createdAt, categoryDetails.amount(), budgetId);
    }
}