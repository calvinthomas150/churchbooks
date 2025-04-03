package org.churchbooks.churchbooks.transactions.services;

import org.churchbooks.churchbooks.transactions.dtos.BudgetDetails;
import org.churchbooks.churchbooks.transactions.entities.Budget;

import java.util.List;
import java.util.UUID;

public interface BudgetService {
    Budget save(BudgetDetails budgetDetails);
    List<Budget> findAll();
    Budget findById(UUID id);
    Budget update(UUID id, BudgetDetails budgetDetails);
}
