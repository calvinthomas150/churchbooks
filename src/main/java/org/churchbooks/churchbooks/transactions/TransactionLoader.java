package org.churchbooks.churchbooks.transactions;

import com.webcohesion.ofx4j.domain.data.MessageSetType;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.banking.BankingResponseMessageSet;
import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;
import com.webcohesion.ofx4j.io.OFXParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TransactionLoader {
    private final Logger logger = LoggerFactory.getLogger(TransactionLoader.class);

    public List<Transaction> loadTransactionsFromURI(String filePath) throws IOException, OFXParseException, URISyntaxException {
        try {
            InputStream inputStream = readFromURI(filePath);
            return parseTransactions(inputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    public List<Transaction> loadTransactionsFromLocalFile(String filePath) throws IOException, OFXParseException {
        try {
            InputStream inputStream = readLocalFile(filePath);
            return parseTransactions(inputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    private InputStream readLocalFile(String filePath) throws FileNotFoundException {
        return new FileInputStream(Path.of(filePath).toFile());
    }

    private InputStream readFromURI(String fileURI) throws IOException, URISyntaxException {
        URL urlObject = new URI(fileURI).toURL();
        URLConnection urlConnection = urlObject.openConnection();
        return urlConnection.getInputStream();
    }

    private List<Transaction> parseTransactions(InputStream inputStream) throws IOException, OFXParseException {
        AggregateUnmarshaller<ResponseEnvelope> unmarshaller = new AggregateUnmarshaller<>(ResponseEnvelope.class);
        ResponseEnvelope responseEnvelope = unmarshaller.unmarshal(inputStream);
        BankingResponseMessageSet bankSet = (BankingResponseMessageSet) responseEnvelope.getMessageSet(MessageSetType.banking);
        try {
            return new ArrayList<>(bankSet.getStatementResponses().stream()
                    .flatMap(bankStatement -> bankStatement.getMessage()
                            .getTransactionList()
                            .getTransactions()
                            .stream()
                    )
                    .toList());
        } catch (NullPointerException e){
            logger.debug("Could not parse transactions, returning empty list instead: ", e);
            return Collections.emptyList();
        }
    }
}