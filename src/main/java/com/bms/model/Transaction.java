package com.bms.model;

import java.time.LocalDateTime;

/**
 * Represents an immutable financial transaction.
 */
public class Transaction {
    private final String transactionId;
    private final String accountNumber;
    private final String type; // e.g., DEPOSIT, WITHDRAW
    private final double amount;
    private final LocalDateTime timestamp;
    private final double balanceAfter;

    public Transaction(String transactionId, String accountNumber, String type, double amount, LocalDateTime timestamp, double balanceAfter) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.balanceAfter = balanceAfter;
    }

    public String getTransactionId() { return transactionId; }
    public String getAccountNumber() { return accountNumber; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public double getBalanceAfter() { return balanceAfter; }
}
