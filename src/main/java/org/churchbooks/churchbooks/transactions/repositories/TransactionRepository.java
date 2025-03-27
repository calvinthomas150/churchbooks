package org.churchbooks.churchbooks.transactions.repositories;

import org.churchbooks.churchbooks.transactions.entities.Transactions;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepository extends ListCrudRepository<Transactions, UUID> {
}
