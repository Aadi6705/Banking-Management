package com.bms.model;

import java.time.LocalDateTime;

/**
 * Represents a Current Account with no minimum balance requirement.
 */
public class CurrentAccount extends Account {

    public CurrentAccount(String accountNumber, String ownerName, double balance, String pinHash, LocalDateTime createdDate, String status) {
        super(accountNumber, ownerName, balance, pinHash, createdDate, status);
    }

    @Override
    public void applyAccountRules() {
        if (getBalance() < 0) {
            throw new IllegalStateException("Current Account balance cannot be negative.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount < 0) {
            throw new IllegalArgumentException("Withdrawal denied: Insufficient balance.");
        }
        super.withdraw(amount);
    }
}
