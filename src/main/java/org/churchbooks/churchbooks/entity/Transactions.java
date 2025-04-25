package org.churchbooks.churchbooks.entity;

import org.churchbooks.churchbooks.dto.TransactionDetails;
import org.churchbooks.churchbooks.enums.Status;
import org.churchbooks.churchbooks.enums.TransactionType;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public record Transactions(
        @Id UUID id,
        Timestamp createdAt,
        TransactionType transactionType,
        Timestamp datePosted,
        BigDecimal amount,
        Status status,
        String memo,
        UUID budgetId,
        UUID categoryId
) {
    public static Transactions fromTransactionDetails(TransactionDetails transactionDetails) {
        return new Transactions(
                null,
                Timestamp.from(Instant.now()),
                transactionDetails.transactionType(),
                transactionDetails.datePosted(),
                transactionDetails.amount(),
                transactionDetails.status(),
                transactionDetails.memo(),
                transactionDetails.budgetId(),
                transactionDetails.categoryId()
        );
    }

    public Transactions update(TransactionDetails transactionDetails) {
        return new Transactions(
                this.id,
                this.createdAt,
                transactionDetails.transactionType(),
                transactionDetails.datePosted(),
                transactionDetails.amount(),
                transactionDetails.status(),
                transactionDetails.memo(),
                transactionDetails.budgetId(),
                transactionDetails.categoryId()
        );
    }
}