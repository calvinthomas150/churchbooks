package org.churchbooks.churchbooks.transactions.services;

import org.churchbooks.churchbooks.transactions.dtos.CategoryDetails;
import org.churchbooks.churchbooks.transactions.entities.Budget;
import org.churchbooks.churchbooks.transactions.entities.Category;
import org.churchbooks.churchbooks.transactions.exception.ResourceNotFoundException;
import org.churchbooks.churchbooks.transactions.repositories.BudgetRepository;
import org.churchbooks.churchbooks.transactions.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BudgetRepository budgetRepository;

    public CategoryServiceImpl(@Autowired CategoryRepository categoryRepository, @Autowired BudgetRepository budgetRepository) {
        this.categoryRepository = categoryRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Category save(CategoryDetails categoryDetails) {
        Budget budget = budgetRepository.findById(categoryDetails.budgetId())
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found. Id:" + categoryDetails.budgetId()));
        budgetRepository.save(budget.updateAllocated(budget.allocated().add(categoryDetails.amount())));

        return categoryRepository.save(new Category(
                categoryDetails.name(),
                categoryDetails.amount(),
                categoryDetails.budgetId()
        ));
    }

    @Override
    @Transactional
    public Category update(UUID categoryId, CategoryDetails categoryDetails) throws ResourceNotFoundException {
        Budget budget = budgetRepository.findById(categoryDetails.budgetId())
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found. Id:" + categoryDetails.budgetId()));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found. Id: " + categoryId));
        BigDecimal difference = categoryDetails.amount().subtract(category.amount());
        budgetRepository.save(budget.updateAllocated(budget.allocated().add(difference)));

        return categoryRepository.save(category.updateCategory(categoryDetails));
    }

}
