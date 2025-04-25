package org.churchbooks.churchbooks.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.churchbooks.churchbooks.entity.Receipt;
import org.churchbooks.churchbooks.service.ReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Operation(summary = "Save a receipt against a transaction", description = "Stores a receipt and returns the entry in the database")
    @PostMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Receipt save(@PathVariable UUID transactionId, @RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
        return receiptService.save(transactionId, file);

    }

    @Operation(summary = "Find a receipt by transaction id", description = "Returns a receipt for a given transaction id")
    @GetMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Receipt findByTransactionId(@PathVariable UUID transactionId){
        return receiptService.findByTransactionId(transactionId);
    }

}
