package org.churchbooks.churchbooks.transactions.services;

import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.io.OFXParseException;
import org.churchbooks.churchbooks.transactions.entities.Transactions;
import org.churchbooks.churchbooks.transactions.repositories.TransactionRepository;
import org.churchbooks.churchbooks.transactions.util.TransactionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionLoader transactionLoader;
    private final TransactionRepository transactionRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);


    public TransactionServiceImpl(@Autowired TransactionLoader _transactionLoader, @Autowired TransactionRepository _transactionRepository) {
        transactionLoader = _transactionLoader;
        transactionRepository = _transactionRepository;
    }

    @Override
    public List<Transactions> save(InputStream inputStream) throws IOException, OFXParseException {
        logger.debug("Parsing OFX file");
        List<Transaction> transactions = transactionLoader.parseTransactions(inputStream);
        logger.debug("Saving parsed transactions to database");
        return transactionRepository.saveAll(transactions.stream()
                .map(Transactions::fromOfxTransaction)
                .collect(Collectors.toList())
        );
    }


    @Override
    public List<Transactions> findAll(){
     return transactionRepository.findAll();
    }

}
