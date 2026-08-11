package com.bms.repository.impl;

import com.bms.model.Account;
import com.bms.model.CurrentAccount;
import com.bms.model.SavingsAccount;
import com.bms.repository.AccountRepository;
import com.bms.util.FileManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * File-based implementation of AccountRepository using CSV format.
 * Format: accountNumber,ownerName,accountType,balance,pinHash,createdDate,status
 */
public class FileAccountRepository implements AccountRepository {
    private final String filePath;

    public FileAccountRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Account account) {
        List<Account> accounts = findAll();
        boolean updated = false;

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(account.getAccountNumber())) {
                accounts.set(i, account);
                updated = true;
                break;
            }
        }

        if (!updated) {
            accounts.add(account);
        }

        saveAll(accounts);
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return findAll().stream()
            .filter(a -> a.getAccountNumber().equals(accountNumber))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            List<String> lines = FileManager.readAllLines(filePath);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                accounts.add(parseAccount(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("System error: Could not read accounts data file.", e);
        } catch (Exception e) {
            // Fail fast on corrupt data per Implementation Plan Option A
            System.err.println("[ERROR] Corrupted data found in accounts file. Please fix manually.");
            e.printStackTrace();
            System.exit(1);
        }
        return accounts;
    }

    private void saveAll(List<Account> accounts) {
        List<String> lines = new ArrayList<>();
        for (Account account : accounts) {
            lines.add(serializeAccount(account));
        }
        try {
            FileManager.writeAllLines(filePath, lines);
        } catch (IOException e) {
            throw new RuntimeException("System error: Could not write to accounts data file.", e);
        }
    }

    private Account parseAccount(String line) {
        String[] parts = line.split(",");
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid account data format: " + line);
        }

        String accountNumber = parts[0];
        String ownerName = parts[1];
        String accountType = parts[2];
        double balance = Double.parseDouble(parts[3]);
        String pinHash = parts[4];
        LocalDateTime createdDate = LocalDateTime.parse(parts[5]);
        String status = parts[6];

        if ("SAVINGS".equalsIgnoreCase(accountType)) {
            return new SavingsAccount(accountNumber, ownerName, balance, pinHash, createdDate, status);
        } else if ("CURRENT".equalsIgnoreCase(accountType)) {
            return new CurrentAccount(accountNumber, ownerName, balance, pinHash, createdDate, status);
        } else {
            throw new IllegalArgumentException("Unknown account type: " + accountType);
        }
    }

    private String serializeAccount(Account account) {
        String accountType = account instanceof SavingsAccount ? "SAVINGS" : "CURRENT";
        return String.join(",",
            account.getAccountNumber(),
            account.getOwnerName(),
            accountType,
            String.valueOf(account.getBalance()),
            account.getPinHash(),
            account.getCreatedDate().toString(),
            account.getStatus()
        );
    }
}
