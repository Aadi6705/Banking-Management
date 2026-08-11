package com.bms.exception;

/**
 * Thrown when there is a critical system configuration issue.
 */
public class SystemConfigurationException extends RuntimeException {
    public SystemConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
