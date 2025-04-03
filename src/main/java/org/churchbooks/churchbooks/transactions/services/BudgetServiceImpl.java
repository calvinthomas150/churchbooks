package org.churchbooks.churchbooks.transactions.services;

import org.churchbooks.churchbooks.transactions.dtos.BudgetDetails;
import org.churchbooks.churchbooks.transactions.entities.Budget;
import org.churchbooks.churchbooks.transactions.exception.ResourceNotFoundException;
import org.churchbooks.churchbooks.transactions.repositories.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BudgetServiceImpl implements BudgetService{
    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(@Autowired BudgetRepository budgetRepository) {
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
                budgetDetails.amount()
        );
        return budgetRepository.save(budget);
    }

    @Override
    @Transactional
    public Budget update(UUID budgetId, BudgetDetails budgetDetails){
         Budget budget = budgetRepository.findById(budgetId)
                 .orElseThrow(() -> new ResourceNotFoundException("Budget not found. Id:" + budgetId));

         return budgetRepository.save(budget.updateBudget(budgetDetails));
    }
}
