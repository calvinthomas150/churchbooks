package org.churchbooks.churchbooks.service;

import com.webcohesion.ofx4j.io.OFXParseException;
import org.churchbooks.churchbooks.entity.Transactions;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface TransactionService {
    List<Transactions> save(InputStream inputStream) throws IOException, OFXParseException;

    List<Transactions> findAll();
}
