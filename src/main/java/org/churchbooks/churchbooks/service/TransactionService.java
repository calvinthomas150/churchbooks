package org.churchbooks.churchbooks.service;

import com.webcohesion.ofx4j.io.OFXParseException;
import org.churchbooks.churchbooks.dto.TransactionDetails;
import org.churchbooks.churchbooks.entity.Transactions;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    List<TransactionDetails> parseOfx(MultipartFile file) throws IOException, OFXParseException;
    Transactions save(TransactionDetails transactionDetails);
    Transactions update(UUID transactionId, TransactionDetails transactionDetails);
    List<Transactions> findAll();
}
