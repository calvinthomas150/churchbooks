package org.churchbooks.churchbooks.dto;

import com.webcohesion.ofx4j.domain.data.common.Transaction;
import org.churchbooks.churchbooks.enums.Status;
import org.churchbooks.churchbooks.enums.TransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

public record TransactionDetails (
                                  TransactionType transactionType,
                                  Timestamp datePosted,
                                  BigDecimal amount,
                                  Status status,
                                  String memo,
                                  String ofxId,
                                  UUID budgetId,
                                  UUID categoryId
) {
    public static TransactionDetails fromOfx(Transaction transaction) {

        return new TransactionDetails(
                TransactionType.CREDIT,
                Timestamp.from(transaction.getDatePosted().toInstant()),
                transaction.getBigDecimalAmount(),
                Status.NEW,
                transaction.getMemo(),
                transaction.getId(),
                null,
                null
        );
    }
}