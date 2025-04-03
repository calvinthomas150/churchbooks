package org.churchbooks.churchbooks.exception;

public class FileValidationException extends RuntimeException {
    public FileValidationException(String errorMessage) {
        super(errorMessage);
    }
}
