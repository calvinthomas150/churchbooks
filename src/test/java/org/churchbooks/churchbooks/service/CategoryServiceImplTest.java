package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.dto.CategoryDetails;
import org.churchbooks.churchbooks.entity.Category;
import org.churchbooks.churchbooks.exception.ResourceNotFoundException;
import org.churchbooks.churchbooks.repository.CategoryRepository;
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
import static org.junit.jupiter.api.Assertions.*;

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
        Category category = categoryService.save(new CategoryDetails("mock", BigDecimal.valueOf(20)));
        assertTrue(categoryRepository.existsById(category.id()));
    }

    @Test
    void update(){
        Category category = categoryService.save(new CategoryDetails("mock", BigDecimal.valueOf(20)));
        Category newCategory =
                categoryService.update(category.id(), new CategoryDetails("newValue", BigDecimal.valueOf(30)));
        assertEquals("newValue", newCategory.name());
    }

    @Test
    void updateWithNoCategory(){
        CategoryDetails mockCategory = new CategoryDetails("mockValue", BigDecimal.valueOf(30));
        UUID randomId = UUID.randomUUID();
        assertThrows(
                ResourceNotFoundException.class,
                () -> categoryService.update(randomId, mockCategory)
        );
    }
}