package com.bms.service;

import com.bms.exception.AccountValidationException;
import com.bms.model.Account;
import com.bms.repository.AccountRepository;
import com.bms.util.PasswordUtil;

/**
 * Service for handling user authentication.
 */
public class AuthService {
    private final AccountRepository repository;

    public AuthService(AccountRepository repository) {
        this.repository = repository;
    }

    /**
     * Authenticates a user using their account number and PIN.
     * @param accountNumber The account number
     * @param rawPin The plain-text PIN
     * @return The authenticated Account object
     * @throws AccountValidationException if authentication fails
     */
    public Account login(String accountNumber, String rawPin) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new AccountValidationException("Account number cannot be empty.");
        }
        if (rawPin == null || rawPin.trim().isEmpty()) {
            throw new AccountValidationException("PIN cannot be empty.");
        }

        Account account = repository.findByAccountNumber(accountNumber.trim());
        
        if (account == null) {
            throw new AccountValidationException("Invalid account number or PIN.");
        }

        if (!PasswordUtil.verifyPin(rawPin, account.getPinHash())) {
            throw new AccountValidationException("Invalid account number or PIN.");
        }

        return account;
    }
}
