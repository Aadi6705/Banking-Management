package com.bms.service;

import com.bms.exception.AccountValidationException;
import com.bms.model.Account;
import com.bms.model.CurrentAccount;
import com.bms.model.SavingsAccount;
import com.bms.repository.AccountRepository;
import com.bms.util.IdGenerator;
import com.bms.util.PasswordUtil;

import java.time.LocalDateTime;

/**
 * Service handling account-related business logic.
 */
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates a new account, validating input and generating a unique ID.
     */
    public Account createAccount(String name, String type, String pin, double initialDeposit) {
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new AccountValidationException("Account owner name cannot be empty.");
        }

        // Validate PIN (Option A: exactly 4 digits)
        if (pin == null || !pin.matches("\\d{4}")) {
            throw new AccountValidationException("PIN must be exactly 4 numeric digits.");
        }

        // Validate type
        if (!type.equalsIgnoreCase("SAVINGS") && !type.equalsIgnoreCase("CURRENT")) {
            throw new AccountValidationException("Invalid account type. Must be SAVINGS or CURRENT.");
        }

        // Validate initial deposit for Savings
        if (type.equalsIgnoreCase("SAVINGS") && initialDeposit < SavingsAccount.MINIMUM_BALANCE) {
            throw new AccountValidationException("Savings account requires a minimum initial deposit of ₹" + SavingsAccount.MINIMUM_BALANCE);
        } else if (initialDeposit < 0) {
            throw new AccountValidationException("Initial deposit cannot be negative.");
        }

        // Generate a unique account number
        String accountNumber;
        do {
            accountNumber = IdGenerator.generateAccountNumber();
        } while (repository.findByAccountNumber(accountNumber) != null);

        // Hash PIN
        String pinHash = PasswordUtil.hashPin(pin);

        Account account;
        if (type.equalsIgnoreCase("SAVINGS")) {
            account = new SavingsAccount(accountNumber, name, initialDeposit, pinHash, LocalDateTime.now(), "ACTIVE");
        } else {
            account = new CurrentAccount(accountNumber, name, initialDeposit, pinHash, LocalDateTime.now(), "ACTIVE");
        }

        // Save and return
        repository.save(account);
        return account;
    }
}
