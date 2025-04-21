package org.churchbooks.churchbooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.churchbooks.churchbooks.dto.BudgetDetails;
import org.churchbooks.churchbooks.enums.Frequency;
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

import static org.churchbooks.churchbooks.InitialTestData.defaultBudgetId;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class BudgetControllerTest {

    @Autowired
    BudgetController budgetController;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Request to find all budgets is successful")
    void findAll() throws Exception {
        this.mockMvc.perform(get("/budgets"))
                .andDo(log())
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Request to find a budgets by id is successful")
    void findById() throws Exception {
        this.mockMvc.perform(get("/budgets/" + defaultBudgetId))
                .andDo(log())
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Request to save a budget entry is successful")
    void saveBudget() throws Exception {
        BudgetDetails mockBudget = new BudgetDetails( "mock", BigDecimal.valueOf(10), Frequency.ONCE);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/budgets")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockBudget));
        this.mockMvc.perform(mockRequest).andDo(log())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.total", notNullValue()));
    }

    @Test
    @DisplayName("Request to update a budget entry is successful")
    void update() throws Exception {
        BudgetDetails mockCategory = new BudgetDetails("mock", BigDecimal.valueOf(50), Frequency.ONCE);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/budgets/" + defaultBudgetId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockCategory));

        this.mockMvc.perform(mockRequest).andDo(log())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.total", equalTo(50)));

    }

}
