package com.bms.exception;

/**
 * Thrown when there is an issue reading from or writing to the persistent storage.
 */
public class DataStorageException extends RuntimeException {
    public DataStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
