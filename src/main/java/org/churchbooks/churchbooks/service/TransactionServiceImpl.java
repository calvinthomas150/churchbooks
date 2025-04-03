package org.churchbooks.churchbooks.service;

import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.io.OFXParseException;
import org.churchbooks.churchbooks.entity.Transactions;
import org.churchbooks.churchbooks.repository.TransactionRepository;
import org.churchbooks.churchbooks.util.TransactionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionLoader transactionLoader;
    private final TransactionRepository transactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);


    public TransactionServiceImpl(@Autowired TransactionLoader transactionLoader, @Autowired TransactionRepository transactionRepository) {
        this.transactionLoader = transactionLoader;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transactions> save(InputStream inputStream) throws IOException, OFXParseException {
        logger.debug("Parsing transactions from file...");
        List<Transaction> transactions = transactionLoader.parseTransactions(inputStream);
        logger.debug("Persisting transactions to database...");
        return transactionRepository.saveAll(transactions.stream()
                .map(Transactions::fromOfxTransaction).toList());

    }

    @Override
    public List<Transactions> findAll(){
        return transactionRepository.findAll();
    }

}
