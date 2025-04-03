package org.churchbooks.churchbooks.exception;

public class StorageException extends RuntimeException {
    public StorageException(Exception exception) {
        super(exception);
    }
}
