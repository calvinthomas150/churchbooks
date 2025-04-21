package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.dto.BudgetDetails;
import org.churchbooks.churchbooks.entity.Budget;
import org.churchbooks.churchbooks.exception.ResourceNotFoundException;
import org.churchbooks.churchbooks.repository.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BudgetServiceImpl implements BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public List<Budget> findAll() {
        return budgetRepository.findAll();
    }

    @Override
    public Budget findById(UUID id) {
        return budgetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Budget not found. Id:" + id));
    }

    @Override
    public Budget save(BudgetDetails budgetDetails) {
        Budget budget = new Budget(
                budgetDetails.name(),
                budgetDetails.amount(),
                budgetDetails.frequency()
        );
        return budgetRepository.save(budget);
    }

    @Override
    @Transactional
    public Budget update(UUID budgetId, BudgetDetails budgetDetails){
         Budget budget = budgetRepository.findById(budgetId)
                 .orElseThrow(() -> new ResourceNotFoundException("Budget not found. Id:" + budgetId));

         return budgetRepository.save(budget.update(budgetDetails));
    }
}
