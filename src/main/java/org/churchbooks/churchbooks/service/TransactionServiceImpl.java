package org.churchbooks.churchbooks.service;

import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.io.OFXParseException;
import org.churchbooks.churchbooks.dto.TransactionDetails;
import org.churchbooks.churchbooks.entity.Budget;
import org.churchbooks.churchbooks.entity.Transactions;
import org.churchbooks.churchbooks.exception.ResourceNotFoundException;
import org.churchbooks.churchbooks.repository.BudgetRepository;
import org.churchbooks.churchbooks.repository.CategoryRepository;
import org.churchbooks.churchbooks.repository.TransactionRepository;
import org.churchbooks.churchbooks.util.TransactionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionLoader transactionLoader;
    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);


    public TransactionServiceImpl(
            TransactionLoader transactionLoader,
            TransactionRepository transactionRepository,
            BudgetRepository budgetRepository,
            CategoryRepository categoryRepository)
    {
        this.transactionLoader = transactionLoader;
        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    private void validateCategory(UUID categoryId){
        logger.info("Validating category with id: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)){
            throw new ResourceNotFoundException(String.format("Category not found with id: %s", categoryId));
        }
    }

    private void updateBudget(UUID budgetId, BigDecimal amount){
        logger.info("Fetching budget with id: {}", budgetId);
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Budget not found. Id: %s", budgetId)));
        budgetRepository.save(budget.updateAllocated(budget.allocated().add(amount)));
    }

    @Override
    public List<TransactionDetails> parseOfx(InputStream inputStream) throws IOException, OFXParseException {
        logger.info("Parsing transactions from file...");
        List<Transaction> transactions = transactionLoader.parseTransactions(inputStream);
        return transactions.stream().map(TransactionDetails::fromOfx).toList();
    }

    @Override
    @Transactional
    public Transactions save(TransactionDetails transactionDetails){
        validateCategory(transactionDetails.categoryId());
        updateBudget(transactionDetails.budgetId(), transactionDetails.amount());
        logger.info("Saving transaction to database...");
        return transactionRepository.save(Transactions.fromTransactionDetails(transactionDetails));
    }

    @Override
    @Transactional
    public Transactions update(UUID transactionId, TransactionDetails transactionDetails){
        validateCategory(transactionDetails.categoryId());
        logger.info("Fetching transaction with id: {}", transactionId);
        Transactions oldTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found. Id:" + transactionId));
        updateBudget(transactionDetails.budgetId(), transactionDetails.amount().subtract(oldTransaction.amount()));
        logger.debug("Updating transaction in database...");
        return transactionRepository.save(oldTransaction.update(transactionDetails));
    }

    @Override
    public List<Transactions> findAll(){
        return transactionRepository.findAll();
    }


}
