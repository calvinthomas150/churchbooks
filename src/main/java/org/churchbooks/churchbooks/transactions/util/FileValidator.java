package org.churchbooks.churchbooks.transactions.util;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidator {

    void validate(MultipartFile file);
}
