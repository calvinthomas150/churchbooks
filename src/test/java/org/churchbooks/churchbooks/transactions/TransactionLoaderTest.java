package org.churchbooks.churchbooks.transactions;

import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.io.OFXParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionLoaderTest {
    private final TransactionLoader loader = new TransactionLoader();

    @Test
    @DisplayName("When a valid file is provided the transactions are loaded successfully")
    void loadValidTestFile() throws IOException, OFXParseException {
        List<Transaction> transactions = loader.loadTransactionsFromLocalFile("src/test/resources/example.ofx");
        assertEquals(10, transactions.size());
        assertEquals(4000, transactions.getFirst().getAmount());
    }

    @Test
    @DisplayName("When an empty file is provided no transactions are loaded")
    void loadEmptyTestFile() throws IOException, OFXParseException {
        List<Transaction> transactions = loader.loadTransactionsFromLocalFile("src/test/resources/empty.ofx");
        assertEquals(0, transactions.size());
    }

    @Test
    @DisplayName("When a missing file is provided throw FileNotFoundException")
    void loadMissingTestFile() {
        assertThrows(
                FileNotFoundException.class,
                () -> loader.loadTransactionsFromLocalFile("")
        );
    }

    @Test
    @DisplayName("When a missing file is provided throw FileNotFoundException")
    void loadInvalidTestFile() {
        assertThrows(
                OFXParseException.class,
                () -> loader.loadTransactionsFromLocalFile("src/test/resources/invalid.ofx")
        );
    }

    @Test
    @DisplayName("When a valid link is provided the transactions are loaded successfully")
    void loadValidTestURI() throws IOException, URISyntaxException, OFXParseException {
        List<Transaction> transactions = loader.loadTransactionsFromURI(
                "https://raw.githubusercontent.com/ftomassetti/ofx-java/master/src/test/resources/example.ofx"
        );
        assertEquals(10, transactions.size());
        assertEquals(4000, transactions.getFirst().getAmount());
    }

    @Test
    @DisplayName("When an invalid link is provided throw URISyntaxException")
    void loadInvalidTestURI() {
        assertThrows(
                URISyntaxException.class,
                () -> loader.loadTransactionsFromURI("https://")
        );
    }
}