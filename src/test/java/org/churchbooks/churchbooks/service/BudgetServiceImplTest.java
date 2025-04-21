package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.dto.BudgetDetails;
import org.churchbooks.churchbooks.entity.Budget;
import org.churchbooks.churchbooks.repository.BudgetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.churchbooks.churchbooks.InitalTestData.budgetId;

/** Integration and data transformation tests*/
@SpringBootTest
@Testcontainers
class BudgetServiceImplTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    BudgetService budgetService;

    @Autowired
    BudgetRepository budgetRepository;

    @Test
    void findAll() {
        List<Budget> budgets = budgetService.findAll();
        assertThat(budgets).isNotNull();
    }

    @Test
    void findById() {
        Budget budget = budgetService.findById(budgetId);
        assertThat(budget).isNotNull();
    }

    @Test
    void save() {
        BudgetDetails mockBudget = new BudgetDetails("mock", BigDecimal.valueOf(20));
        Budget budget = budgetService.save(mockBudget);
        assertThat(budgetRepository.existsById(budget.id())).isTrue();
    }

    @Test
    void update(){
        BudgetDetails mockBudget = new BudgetDetails("mockValue", BigDecimal.valueOf(30));
        Budget budget = budgetService.update(budgetId, mockBudget);
        assertThat(budget.name()).isEqualTo("mockValue");
    }

}