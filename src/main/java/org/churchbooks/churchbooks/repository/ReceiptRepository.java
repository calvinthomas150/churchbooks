package org.churchbooks.churchbooks.repository;

import org.churchbooks.churchbooks.entity.Receipt;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReceiptRepository extends ListCrudRepository<Receipt, UUID> {
    Optional<Receipt> findByTransactionId(UUID transactionId);
}