package org.churchbooks.churchbooks.transactions;

import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.io.OFXParseException;
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
    void loadTransactions_ValidFileTest() throws IOException, OFXParseException {
        List<Transaction> transactions = loader.loadTransactionsFromLocalFile("src/test/resources/example.ofx");
        assertEquals(10, transactions.size());
        assertEquals(4000, transactions.getFirst().getAmount());
    }

    @Test
    void loadTransactions_MissingFileTest() {
        assertThrows(
                FileNotFoundException.class,
                () -> loader.loadTransactionsFromLocalFile("")
        );
    }

    @Test
    void loadTransactions_InvalidFileTest() {
        assertThrows(
                OFXParseException.class,
                () -> loader.loadTransactionsFromLocalFile("src/test/resources/invalid.ofx")
        );
    }

    @Test
    void loadTransactionsFromUrl_ValidFileTest() throws IOException, URISyntaxException, OFXParseException {
        List<Transaction> transactions = loader.loadTransactionsFromURI(
                "https://raw.githubusercontent.com/ftomassetti/ofx-java/master/src/test/resources/example.ofx"
        );
        assertEquals(10, transactions.size());
        assertEquals(4000, transactions.getFirst().getAmount());
    }

    @Test
    void loadTransactionsFromUrl_InvalidURITest() {
        assertThrows(
                URISyntaxException.class,
                () -> loader.loadTransactionsFromURI("https://")
        );
    }
}