package org.churchbooks.churchbooks.controller;

import org.churchbooks.churchbooks.util.StorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.net.URI;

import static org.churchbooks.churchbooks.InitialTestData.defaultTransactionId;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class ReceiptControllerTest {

    @Autowired
    ReceiptController receiptController;
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private StorageService storageService;

    @Test
    @DisplayName("Request to find the receipt for a transaction successful")
    void findByTransactionId() throws Exception {
        this.mockMvc.perform(get("/transactions/" + defaultTransactionId + "/receipts"))
                .andDo(log())
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("Request to save a receipt against a transaction is successful")
    void save() throws Exception{

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "example.png",
                "image/png",
                new FileInputStream("src/test/resources/example.png")
        );
        Mockito.when(storageService.store(multipartFile)).thenReturn(URI.create("src/test/resources/example.png"));

        this.mockMvc.perform(multipart("/transactions/" + defaultTransactionId + "/receipts").file(multipartFile))
                .andDo(log())
                .andExpect(status().isCreated());

        then(this.storageService).should().store(eq(multipartFile));
    }

}
