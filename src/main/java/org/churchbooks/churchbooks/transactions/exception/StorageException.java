package org.churchbooks.churchbooks.transactions.exception;

public class StorageException extends RuntimeException {
    public StorageException(Exception exception) {
        super(exception);
    }
}
