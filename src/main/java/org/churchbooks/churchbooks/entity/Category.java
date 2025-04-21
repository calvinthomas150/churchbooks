package org.churchbooks.churchbooks.entity;

import org.churchbooks.churchbooks.dto.CategoryDetails;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public record Category (
        @Id UUID id,
        String name,
        Timestamp createdAt,
        BigDecimal amount
){
    public Category(String name, BigDecimal amount) {
        this(null, name, Timestamp.from(Instant.now()), amount);
    }

    public Category updateCategory(CategoryDetails categoryDetails){
        return new Category(id, categoryDetails.name(), createdAt, categoryDetails.amount());
    }
}