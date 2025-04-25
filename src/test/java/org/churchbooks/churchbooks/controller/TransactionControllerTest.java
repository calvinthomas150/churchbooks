package org.churchbooks.churchbooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.churchbooks.churchbooks.dto.TransactionDetails;
import org.churchbooks.churchbooks.enums.Status;
import org.churchbooks.churchbooks.enums.TransactionType;
import org.churchbooks.churchbooks.util.StorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.churchbooks.churchbooks.InitialTestData.defaultBudgetId;
import static org.churchbooks.churchbooks.InitialTestData.defaultCategoryId;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Input and response validation tests*/
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class TransactionControllerTest {

    @Autowired
    TransactionController transactionController;
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private StorageService storageService;

    @Test
    void contextLoads() {
        assertNotNull(transactionController, "Failed to inject controller instance");
    }

    @Test
    @DisplayName("Request to find all transactions is successful")
    void findAll() throws Exception {
        this.mockMvc.perform(get("/transactions"))
                .andDo(log())
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Request to save TransactionDetails is successful")
    void saveAll() throws Exception {
        TransactionDetails mockTransaction = new TransactionDetails(
                TransactionType.CREDIT,
                Timestamp.from(Instant.now()),
                BigDecimal.valueOf(20),
                Status.NEW,
                "Test",
                "ID",
                defaultBudgetId,
                defaultCategoryId
        );
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mockTransaction));
        this.mockMvc.perform(mockRequest).andDo(log())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.amount", comparesEqualTo(20)));
    }

    @Test
    @DisplayName("Request to parse uploaded ofx file is successful")
    void parseOfx() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "example.ofx",
                "text/plain",
                new FileInputStream("src/test/resources/example.ofx")
        );

        this.mockMvc.perform(multipart("/transactions/parse").file(multipartFile))
                .andDo(log())
                .andExpect(status().isOk());

        then(this.storageService).should().store(eq(multipartFile), anyString());
    }

    @Test
    @DisplayName("Request to parse ofx file fails when ofx file cannot be parsed")
    void shouldFailOnInvalidOFXFile() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "invalid.ofx",
                "text/plain",
                new FileInputStream("src/test/resources/invalid.ofx"));

        this.mockMvc.perform(multipart("/transactions/parse").file(multipartFile)).andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Request to parse ofx file fails when file is unsupported type")
    void shouldFailOnUnsupportedFileType() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "unsupported.ofx",
                "application/x-ofx",
                new FileInputStream("src/test/resources/unsupported.php"));

        this.mockMvc.perform(multipart("/transactions/parse").file(multipartFile)).andExpect(status().is4xxClientError());
    }

}