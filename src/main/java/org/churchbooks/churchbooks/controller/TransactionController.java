package org.churchbooks.churchbooks.controller;

import com.webcohesion.ofx4j.io.OFXParseException;
import io.swagger.v3.oas.annotations.Operation;
import org.churchbooks.churchbooks.entity.Transactions;
import org.churchbooks.churchbooks.service.TransactionService;
import org.churchbooks.churchbooks.util.OFXFileValidator;
import org.churchbooks.churchbooks.util.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;
    private final StorageService storageService;
    private final OFXFileValidator fileValidator;

    public TransactionController(@Autowired TransactionService transactionService, @Autowired StorageService storageService) {
        this.transactionService = transactionService;
        this.storageService = storageService;
        this.fileValidator = new OFXFileValidator();
    }

    @Operation(summary = "Finds all transactions", description = "Returns a list of transactions")
    @GetMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    List<Transactions> findAll() {
        return transactionService.findAll();
    }

    @Operation(summary = "Save transactions from .ofx file", description = "Returns the list of parsed transactions")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Transactions> handleFileUpload(@Validated @RequestParam("file") MultipartFile file) throws IOException, OFXParseException {
        logger.info("OFX file uploaded by user: {}", file.getOriginalFilename());
        fileValidator.validate(file);
        // give the file a unique name before saving
        String filename = UUID.randomUUID() + ".ofx";
        storageService.store(file, filename);
        try (InputStream inputStream = file.getInputStream()) {
            return transactionService.save(inputStream);
        }
    }

}
