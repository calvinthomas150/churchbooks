package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.entity.Receipt;
import org.churchbooks.churchbooks.repository.TransactionRepository;
import org.churchbooks.churchbooks.util.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.UUID;

import static org.churchbooks.churchbooks.InitialTestData.defaultTransactionId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/** Integration and data transformation tests*/
@SpringBootTest
@Testcontainers
class ReceiptServiceImplTest {
    @Autowired ReceiptService receiptService;
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private StorageService storageService;

    @Test
    void save() throws IOException, URISyntaxException {
        when(transactionRepository.existsById(any(UUID.class))).thenReturn(true);
        when(storageService.store(any(MultipartFile.class))).thenReturn(URI.create("src/test/resources/example.png"));
        String filePath = "src/test/resources/example.png";
        try (InputStream inputStream = new FileInputStream(Path.of(filePath).toFile())) {
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "file",
                    "example.png",
                    "text/plain",
                    inputStream
            );
            Receipt receipt = receiptService.save(defaultTransactionId, multipartFile);
            assertEquals(defaultTransactionId, receipt.transactionId());
        }

    }
}
