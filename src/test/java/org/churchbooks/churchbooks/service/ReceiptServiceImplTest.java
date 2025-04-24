package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.entity.Receipt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.churchbooks.churchbooks.InitialTestData.defaultTransactionId;
import static org.junit.jupiter.api.Assertions.assertEquals;


/** Integration and data transformation tests*/
@SpringBootTest
@Testcontainers
class ReceiptServiceImplTest {
    @Autowired ReceiptService receiptService;

    @Test
    void save() throws IOException, URISyntaxException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "example.png",
                "text/plain",
                new FileInputStream("src/test/resources/example.png")
        );
        Receipt receipt = receiptService.save(defaultTransactionId, multipartFile);
        assertEquals(defaultTransactionId, receipt.transactionId());

    }
}
