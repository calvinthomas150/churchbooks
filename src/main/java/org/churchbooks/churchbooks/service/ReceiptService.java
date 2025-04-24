package org.churchbooks.churchbooks.service;

import org.churchbooks.churchbooks.entity.Receipt;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.UUID;

public interface ReceiptService {
  Receipt save(UUID transactionId, MultipartFile file) throws URISyntaxException, IOException;
  Receipt findByTransactionId(UUID id);
}
