package com.bms.exception;

/**
 * Thrown when account validation fails (e.g., bad PIN, missing name).
 */
public class AccountValidationException extends RuntimeException {
    public AccountValidationException(String message) {
        super(message);
    }
}
