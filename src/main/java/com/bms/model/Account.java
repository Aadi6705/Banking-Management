package com.bms.model;

import java.time.LocalDateTime;

/**
 * Abstract base class representing a bank account.
 */
public abstract class Account {
    private String accountNumber;
    private String ownerName;
    private double balance;
    private String pinHash;
    private LocalDateTime createdDate;
    private String status; // e.g., ACTIVE, CLOSED

    public Account(String accountNumber, String ownerName, double balance, String pinHash, LocalDateTime createdDate, String status) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.pinHash = pinHash;
        this.createdDate = createdDate;
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public double getBalance() {
        return balance;
    }

    public String getPinHash() {
        return pinHash;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Declares abstract method for subclass-specific rules (e.g., minimum balance).
     */
    public abstract void applyAccountRules();

    /**
     * Base deposit logic.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        this.balance += amount;
    }

    /**
     * Base withdraw logic.
     * Note: Subclasses should validate withdrawal conditions before calling super.withdraw().
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        this.balance -= amount;
    }
}
