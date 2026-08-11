package com.bms.service;

import com.bms.exception.InvalidTransactionException;
import com.bms.model.Account;
import com.bms.model.Transaction;
import com.bms.repository.AccountRepository;
import com.bms.repository.TransactionRepository;

import java.time.LocalDateTime;
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
}
