package org.churchbooks.churchbooks.transactions.services;

import com.webcohesion.ofx4j.io.OFXParseException;
import org.churchbooks.churchbooks.transactions.entities.Transactions;
import org.churchbooks.churchbooks.transactions.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/** Integration and data transformation tests*/
@SpringBootTest
@Testcontainers
class TransactionServiceImplTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    void save() throws IOException, OFXParseException {
        String filePath = "src/test/resources/example.ofx";
        try (InputStream inputStream = new FileInputStream(Path.of(filePath).toFile())) {
            transactionService.save(inputStream);
        }
        Assertions.assertEquals(10, transactionRepository.findAll().size());
    }

    @Test
    void findAll() {
        List<Transactions> transactions = transactionService.findAll();
        assertThat(transactions).isNotNull();
    }
}