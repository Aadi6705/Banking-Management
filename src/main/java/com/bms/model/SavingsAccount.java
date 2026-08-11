package com.bms.model;

import java.time.LocalDateTime;

/**
 * Represents a Savings Account with a minimum balance requirement.
 */
public class SavingsAccount extends Account {
    public static final double MINIMUM_BALANCE = 1000.00;

    public SavingsAccount(String accountNumber, String ownerName, double balance, String pinHash, LocalDateTime createdDate, String status) {
        super(accountNumber, ownerName, balance, pinHash, createdDate, status);
    }

    @Override
    public void applyAccountRules() {
        if (getBalance() < MINIMUM_BALANCE) {
            throw new IllegalStateException("Savings Account must maintain a minimum balance of ₹" + MINIMUM_BALANCE);
        }
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() - amount < MINIMUM_BALANCE) {
            throw new IllegalArgumentException(String.format("Withdrawal denied: Minimum balance of ₹%.2f must be maintained.", MINIMUM_BALANCE));
        }
        super.withdraw(amount);
    }
}
