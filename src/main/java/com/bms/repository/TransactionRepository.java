package com.bms.repository;

import com.bms.model.Transaction;
import java.util.List;

public interface TransactionRepository {
    void append(Transaction transaction);
    List<Transaction> findByAccountNumber(String accountNumber);
}
