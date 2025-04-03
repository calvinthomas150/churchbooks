package org.churchbooks.churchbooks.transactions.repositories;

import org.churchbooks.churchbooks.transactions.entities.Budget;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BudgetRepository extends ListCrudRepository<Budget, UUID> {
}
