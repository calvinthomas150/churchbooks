package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.dto.BudgetDetails;
import org.churchbooks.churchbooks.entity.Budget;

import java.util.List;
import java.util.UUID;

public interface BudgetService {
    Budget save(BudgetDetails budgetDetails);
    List<Budget> findAll();
    Budget findById(UUID id);
    Budget update(UUID id, BudgetDetails budgetDetails);
}
