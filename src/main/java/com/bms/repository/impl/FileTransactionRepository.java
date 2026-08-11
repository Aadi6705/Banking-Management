package com.bms.repository.impl;

import com.bms.exception.DataStorageException;
import com.bms.model.Transaction;
import com.bms.repository.TransactionRepository;
import com.bms.util.FileManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileTransactionRepository implements TransactionRepository {
    private final String filePath;

    public FileTransactionRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void append(Transaction transaction) {
        try {
            FileManager.appendLine(filePath, serializeTransaction(transaction));
        } catch (IOException e) {
            throw new DataStorageException("System error: Could not write to transactions data file.", e);
        }
    }

    @Override
    public List<Transaction> findByAccountNumber(String accountNumber) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            List<String> lines = FileManager.readAllLines(filePath);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                Transaction t = parseTransaction(line);
                if (t.getAccountNumber().equals(accountNumber)) {
                    transactions.add(t);
                }
            }
        } catch (IOException e) {
            throw new DataStorageException("System error: Could not read transactions data file.", e);
        }
        return transactions;
    }

    private Transaction parseTransaction(String line) {
        String[] parts = line.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid transaction data format: " + line);
        }
        return new Transaction(
                parts[0], 
                parts[1], 
                parts[2], 
                Double.parseDouble(parts[3]), 
                LocalDateTime.parse(parts[4]), 
                Double.parseDouble(parts[5])
        );
    }

    private String serializeTransaction(Transaction tx) {
        return String.join(",",
                tx.getTransactionId(),
                tx.getAccountNumber(),
                tx.getType(),
                String.valueOf(tx.getAmount()),
                tx.getTimestamp().toString(),
                String.valueOf(tx.getBalanceAfter())
        );
    }
}
