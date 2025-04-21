package org.churchbooks.churchbooks.repository;

import org.churchbooks.churchbooks.entity.Transactions;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends ListCrudRepository<Transactions, UUID> {
}
