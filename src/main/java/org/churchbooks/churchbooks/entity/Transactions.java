package org.churchbooks.churchbooks.entity;

import com.webcohesion.ofx4j.domain.data.common.Transaction;
import org.churchbooks.churchbooks.enums.Source;
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
        Source source
) {
    public static Transactions fromOfxTransaction(Transaction transaction) {
        return new Transactions(
                null,
                Timestamp.from(Instant.now()),
                TransactionType.CREDIT,
                Timestamp.from(transaction.getDatePosted().toInstant()),
                transaction.getBigDecimalAmount(),
                Status.VALID,
                transaction.getMemo(),
                Source.OFX);
    }
}