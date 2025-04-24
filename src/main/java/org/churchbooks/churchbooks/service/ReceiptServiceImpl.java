package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.entity.Receipt;
import org.churchbooks.churchbooks.exception.ResourceNotFoundException;
import org.churchbooks.churchbooks.repository.ReceiptRepository;
import org.churchbooks.churchbooks.repository.TransactionRepository;
import org.churchbooks.churchbooks.util.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final TransactionRepository transactionRepository;
    private final StorageService storageService;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository, TransactionRepository transactionRepository, @Autowired StorageService storageService) {
        this.receiptRepository = receiptRepository;
        this.transactionRepository = transactionRepository;
        this.storageService = storageService;
    }

    @Override
    public Receipt save(UUID transactionId, MultipartFile file) throws URISyntaxException, IOException {
        if(!transactionRepository.existsById(transactionId)){
            throw new ResourceNotFoundException("Transaction not found with id: " + transactionId);
        }
        URI uri = storageService.store(file);
        Receipt receipt = new Receipt(uri, transactionId);
        return receiptRepository.save(receipt);
    }

    @Override
    public Receipt findByTransactionId(UUID transactionId) {
        return receiptRepository.findByTransactionId(transactionId).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Receipt not found for transaction with id: %s", transactionId))
        );
    }
}
