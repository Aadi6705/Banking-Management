package com.bms.service;

import com.bms.exception.InvalidTransactionException;
import com.bms.model.Account;
import com.bms.model.Transaction;
import com.bms.repository.AccountRepository;
import com.bms.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void deposit(Account account, double amount) {
        if (amount <= 0) {
            throw new InvalidTransactionException("Deposit amount must be greater than zero.");
        }

        account.deposit(amount);
        Transaction tx = new Transaction(
                UUID.randomUUID().toString(),
                account.getAccountNumber(),
                "DEPOSIT",
                amount,
                LocalDateTime.now(),
                account.getBalance()
        );

        // Atomic ordering: append log first, then save state
        transactionRepository.append(tx);
        accountRepository.save(account);
    }

    public void withdraw(Account account, double amount) {
        if (amount <= 0) {
            throw new InvalidTransactionException("Withdrawal amount must be greater than zero.");
        }

        // Will throw IllegalArgumentException or IllegalStateException per rules
        try {
            account.withdraw(amount);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new InvalidTransactionException(e.getMessage());
        }

        Transaction tx = new Transaction(
                UUID.randomUUID().toString(),
                account.getAccountNumber(),
                "WITHDRAW",
                amount,
                LocalDateTime.now(),
                account.getBalance()
        );

        // Atomic ordering: append log first, then save state
        transactionRepository.append(tx);
        accountRepository.save(account);
    }

    /**
     * Retrieves the transaction history for a specific account.
     */
    public List<Transaction> getHistory(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty.");
        }
        return transactionRepository.findByAccountNumber(accountNumber.trim());
    }

    /**
     * Transfers funds from one account to another atomically.
     */
    public void transfer(Account sourceAccount, String targetAccountNumber, double amount) {
        if (amount <= 0) {
            throw new InvalidTransactionException("Transfer amount must be greater than zero.");
        }
        if (sourceAccount.getAccountNumber().equalsIgnoreCase(targetAccountNumber.trim())) {
            throw new InvalidTransactionException("Cannot transfer funds to the same account.");
        }

        // Fetch all accounts into memory to modify and save atomically
        List<Account> allAccounts = accountRepository.findAll();
        
        Account targetAccount = allAccounts.stream()
                .filter(a -> a.getAccountNumber().equalsIgnoreCase(targetAccountNumber.trim()))
                .findFirst()
                .orElseThrow(() -> new InvalidTransactionException("Target account not found."));

        // Replace sourceAccount with the instance from our loaded list
        Account sourceAccountFromList = allAccounts.stream()
                .filter(a -> a.getAccountNumber().equals(sourceAccount.getAccountNumber()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Source account not found in repository."));

        // Try withdrawing first
        try {
            sourceAccountFromList.withdraw(amount);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new InvalidTransactionException(e.getMessage());
        }

        // Apply deposit
        targetAccount.deposit(amount);

        // Generate logs
        Transaction txOut = new Transaction(
                UUID.randomUUID().toString(),
                sourceAccountFromList.getAccountNumber(),
                "TRANSFER_OUT",
                amount,
                LocalDateTime.now(),
                sourceAccountFromList.getBalance()
        );

        Transaction txIn = new Transaction(
                UUID.randomUUID().toString(),
                targetAccount.getAccountNumber(),
                "TRANSFER_IN",
                amount,
                LocalDateTime.now(),
                targetAccount.getBalance()
        );

        // Append logs first
        transactionRepository.append(txOut);
        transactionRepository.append(txIn);

        // Atomically rewrite account state
        accountRepository.saveAll(allAccounts);

        // Update the reference passed in so the UI sees the new balance immediately
        sourceAccount.withdraw(amount); 
    }
}
