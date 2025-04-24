package org.churchbooks.churchbooks.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

public interface StorageService {
    URI store(MultipartFile file) throws IOException;
    }
