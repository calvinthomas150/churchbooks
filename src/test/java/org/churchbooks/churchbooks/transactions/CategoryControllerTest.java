package org.churchbooks.churchbooks.transactions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.churchbooks.churchbooks.transactions.controller.CategoryController;
import org.churchbooks.churchbooks.transactions.dtos.CategoryDetails;
import org.churchbooks.churchbooks.transactions.entities.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.churchbooks.churchbooks.transactions.InitalTestData.budgetId;
import static org.churchbooks.churchbooks.transactions.InitalTestData.categoryId;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class CategoryControllerTest {

    @Autowired
    CategoryController categoryController;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Request to find all categories is successful")
    void findAll() throws Exception {
        this.mockMvc.perform(get("/categories"))
                .andDo(log())
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Request to save a category entry is successful")
    void saveCategory() throws Exception {
        Category mockCategory = new Category( null, "mock", Timestamp.from(Instant.now()), BigDecimal.valueOf(10), budgetId);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockCategory));
        this.mockMvc.perform(mockRequest).andDo(log())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.budgetId", equalTo(budgetId.toString())));
    }

    @Test
    @DisplayName("Request to update a category entry is successful")
    void updateCategory() throws Exception {
        CategoryDetails mockCategory = new CategoryDetails("mock", BigDecimal.valueOf(20), budgetId);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/categories/"+categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockCategory));

        this.mockMvc.perform(mockRequest).andDo(log())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.amount", equalTo(20)));

    }
}
