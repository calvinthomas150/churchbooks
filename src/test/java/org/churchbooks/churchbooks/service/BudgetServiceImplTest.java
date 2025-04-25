package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.dto.BudgetDetails;
import org.churchbooks.churchbooks.entity.Budget;
import org.churchbooks.churchbooks.enums.Frequency;
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
import static org.churchbooks.churchbooks.InitialTestData.defaultBudgetId;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Budget budget = budgetService.findById(defaultBudgetId);
        assertThat(budget).isNotNull();
    }

    @Test
    void save() {
        BudgetDetails mockBudget = new BudgetDetails("mock", BigDecimal.valueOf(20), Frequency.ONCE);
        Budget budget = budgetService.save(mockBudget);
        assertThat(budgetRepository.existsById(budget.id())).isTrue();
    }

    @Test
    void update(){
        Budget initialBudget =
                budgetService.save(new BudgetDetails("food", BigDecimal.valueOf(30), Frequency.ONCE));
        budgetRepository.save(initialBudget);
        BudgetDetails newBudgetDetails = new BudgetDetails("utility", BigDecimal.valueOf(20), Frequency.ONCE);
        Budget newBudget = budgetService.update(initialBudget.id(), newBudgetDetails);
        assertEquals(initialBudget.id(), newBudget.id());
        assertThat(newBudget.name()).isEqualTo("utility");
    }

}