package org.churchbooks.churchbooks.transactions.exception;

public class FileValidationException extends RuntimeException {
    public FileValidationException(String errorMessage) {
        super(errorMessage);
    }
}
