package org.churchbooks.churchbooks.repository;

import org.churchbooks.churchbooks.entity.Budget;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BudgetRepository extends ListCrudRepository<Budget, UUID> {
}
