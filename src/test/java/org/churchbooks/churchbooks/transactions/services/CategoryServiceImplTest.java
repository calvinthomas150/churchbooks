package org.churchbooks.churchbooks.transactions.services;

import org.churchbooks.churchbooks.transactions.dtos.CategoryDetails;
import org.churchbooks.churchbooks.transactions.entities.Category;
import org.churchbooks.churchbooks.transactions.exception.ResourceNotFoundException;
import org.churchbooks.churchbooks.transactions.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.churchbooks.churchbooks.transactions.InitalTestData.budgetId;
import static org.churchbooks.churchbooks.transactions.InitalTestData.categoryId;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Integration and data transformation tests*/
@SpringBootTest
@Testcontainers
class CategoryServiceImplTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findAll() {
        List<Category> categories = categoryService.findAll();
        assertThat(categories).isNotNull();
    }

    @Test
    void save() {
        CategoryDetails mockCategory = new CategoryDetails("mock", BigDecimal.valueOf(20), budgetId);
        Category category = categoryService.save(mockCategory);
        assertThat(categoryRepository.existsById(category.id())).isTrue();
    }

    @Test
    void saveWithNoBudget() {
        CategoryDetails mockCategory = new CategoryDetails("mock", BigDecimal.valueOf(20), UUID.randomUUID());
        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.save(mockCategory)
        );
    }

    @Test
    void update(){
        CategoryDetails mockCategory = new CategoryDetails("mockValue", BigDecimal.valueOf(30), budgetId);
        Category category = categoryService.update(categoryId, mockCategory);
        assertThat(category.name()).isEqualTo("mockValue");
    }

    @Test
    void updateWithNoBudget(){
        CategoryDetails mockCategory = new CategoryDetails("mockValue", BigDecimal.valueOf(30), UUID.randomUUID());
        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.update(categoryId, mockCategory)
        );
    }

    @Test
    void updateWithNoCategory(){
        CategoryDetails mockCategory = new CategoryDetails("mockValue", BigDecimal.valueOf(30), budgetId);
        UUID randomId = UUID.randomUUID();
        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.update(randomId, mockCategory)
        );
    }
}