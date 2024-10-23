package org.churchbooks.churchbooks.transactions.services;

import com.webcohesion.ofx4j.io.OFXParseException;
import org.churchbooks.churchbooks.transactions.entities.Transactions;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface TransactionService {
    List<Transactions> save(InputStream inputStream) throws IOException, OFXParseException;

    List<Transactions> findAll();
}
