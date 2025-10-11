package org.churchbooks.churchbooks.entity;

import org.springframework.data.annotation.Id;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public record Receipt(
        @Id UUID id,
        String uri,
        Timestamp createdAt,
        UUID transactionId
) {
    public Receipt(URI uri, UUID transactionId) {
        this(null, uri.toString(), Timestamp.from(Instant.now()), transactionId);
    }
}
