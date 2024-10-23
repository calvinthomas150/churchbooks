package org.churchbooks.churchbooks.transactions.entities;

import com.webcohesion.ofx4j.domain.data.common.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.churchbooks.churchbooks.transactions.enums.Source;
import org.churchbooks.churchbooks.transactions.enums.Status;
import org.churchbooks.churchbooks.transactions.enums.TransactionType;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Table(name = "Transaction")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {
    @Id
    private final UUID id = UUID.randomUUID();
    @Comment(" time transaction started being tracked ")
    private final Timestamp createdAt = Timestamp.from(Instant.now());
    private TransactionType transactionType;
    @Comment("Date the transaction occurred")
    private LocalDateTime datePosted;
    private BigDecimal amount;
    private Status status;
    private String memo;
    private Source source;

    public static Transactions fromOfxTransaction(Transaction transaction) {
        return Transactions.builder()
                .transactionType(TransactionType.CREDIT)
                .datePosted(LocalDateTime.from(transaction.getDatePosted().toInstant().atZone(ZoneId.systemDefault())))
                .amount(transaction.getBigDecimalAmount())
                .status(Status.VALID)
                .memo(transaction.getMemo())
                .source(Source.OFX)
                .build();
    }
}