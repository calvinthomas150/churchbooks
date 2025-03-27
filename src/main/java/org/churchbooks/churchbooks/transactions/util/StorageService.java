package org.churchbooks.churchbooks.transactions.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    void store(MultipartFile file, String filename) throws IOException;
    }
