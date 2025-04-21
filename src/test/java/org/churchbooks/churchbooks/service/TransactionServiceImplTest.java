package org.churchbooks.churchbooks.service;

import com.webcohesion.ofx4j.io.OFXParseException;
import org.churchbooks.churchbooks.dto.TransactionDetails;
import org.churchbooks.churchbooks.entity.Budget;
import org.churchbooks.churchbooks.entity.Transactions;
import org.churchbooks.churchbooks.enums.Frequency;
import org.churchbooks.churchbooks.enums.Status;
import org.churchbooks.churchbooks.enums.TransactionType;
import org.churchbooks.churchbooks.exception.ResourceNotFoundException;
import org.churchbooks.churchbooks.repository.BudgetRepository;
import org.churchbooks.churchbooks.repository.TransactionRepository;
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
import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.churchbooks.churchbooks.InitialTestData.defaultBudgetId;
import static org.churchbooks.churchbooks.InitialTestData.defaultCategoryId;
import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    BudgetRepository budgetRepository;
    
    Budget mockBudget = new Budget("", BigDecimal.ZERO, Frequency.ONCE);

    @Test
    void parseOfx() throws IOException, OFXParseException {
        String filePath = "src/test/resources/example.ofx";
        try (InputStream inputStream = new FileInputStream(Path.of(filePath).toFile())) {
            List<TransactionDetails> transactionDetails = transactionService.parseOfx(inputStream);
            assertEquals(10, transactionDetails.size());
        }
    }

    @Test
    void save() {
        TransactionDetails transactionDetails = new TransactionDetails(
                TransactionType.CREDIT,
                Timestamp.from(Instant.now()),
                BigDecimal.valueOf(10),
                Status.NEW,
                "Test",
                "ID",
                defaultBudgetId,
                defaultCategoryId
        );
        int initialNumberOfTransactions = transactionRepository.findAll().size();
        BigDecimal initialBudgetAllocation = budgetRepository.findById(defaultBudgetId).orElse(mockBudget).allocated();

        transactionService.save(transactionDetails);
        BigDecimal newBudgetAllocation = budgetRepository.findById(defaultBudgetId).orElse(mockBudget).allocated();

        assertEquals(initialNumberOfTransactions + 1, transactionRepository.findAll().size());
        assertEquals(initialBudgetAllocation.add(BigDecimal.TEN), newBudgetAllocation);
    }

    @Test
      void updateBudgetFails() {
        TransactionDetails transactionDetails = new TransactionDetails(
                TransactionType.CREDIT,
                Timestamp.from(Instant.now()),
                BigDecimal.valueOf(10),
                Status.NEW,
                "Test",
                "ID",
                UUID.randomUUID(),
                defaultCategoryId
        );
        assertThrows(
                ResourceNotFoundException.class,
                () -> transactionService.save(transactionDetails)
        );
    }

    @Test
      void validateCategoryFails() {
        TransactionDetails transactionDetails = new TransactionDetails(
                TransactionType.CREDIT,
                Timestamp.from(Instant.now()),
                BigDecimal.valueOf(10),
                Status.NEW,
                "Test",
                "ID",
                defaultBudgetId,
                UUID.randomUUID()
        );
        assertThrows(
                ResourceNotFoundException.class,
                () -> transactionService.save(transactionDetails)
        );
    }

    @Test
    void findAll() {
        List<Transactions> transactions = transactionService.findAll();
        assertNotNull(transactions);
    }

    @Test
    void update(){
        Transactions oldTransaction = transactionService.save(
                new TransactionDetails(
                        TransactionType.CREDIT,
                        Timestamp.from(Instant.now()),
                        BigDecimal.valueOf(8),
                        Status.NEW,
                        "Test",
                        "ID",
                        defaultBudgetId,
                        defaultCategoryId
                )
        );
        BigDecimal initialBudgetAllocation = budgetRepository.findById(defaultBudgetId).orElse(mockBudget).allocated();
        Transactions newTransaction = transactionService.update(
                oldTransaction.id(),
                new TransactionDetails(
                        TransactionType.CREDIT,
                        Timestamp.from(Instant.now()),
                        BigDecimal.valueOf(10),
                        Status.NEW,
                        "Test",
                        "ID",
                        defaultBudgetId,
                        defaultCategoryId
                )
        );
        BigDecimal newBudgetAllocation = budgetRepository.findById(defaultBudgetId).orElse(mockBudget).allocated();
        assertEquals(oldTransaction.id(), newTransaction.id());
        assertEquals(BigDecimal.valueOf(10), newTransaction.amount());
        assertEquals(initialBudgetAllocation.add(BigDecimal.TWO), newBudgetAllocation);
    }
}