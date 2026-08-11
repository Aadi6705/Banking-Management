package com.bms.service;

import com.bms.exception.InvalidTransactionException;
import com.bms.model.Account;
import com.bms.model.SavingsAccount;
import com.bms.model.Transaction;
import com.bms.repository.AccountRepository;
import com.bms.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private AccountRepository mockAccountRepo;
    private TransactionRepository mockTxRepo;
    private TransactionService txService;
    private List<Transaction> txLog;

    @BeforeEach
    void setUp() {
        txLog = new ArrayList<>();
        mockAccountRepo = new AccountRepository() {
            @Override public void save(Account account) {}
            @Override public Account findByAccountNumber(String accountNumber) { return null; }
            @Override public List<Account> findAll() { return new ArrayList<>(); }
        };
        mockTxRepo = new TransactionRepository() {
            @Override public void append(Transaction transaction) { txLog.add(transaction); }
            @Override public List<Transaction> findByAccountNumber(String accountNumber) { return txLog; }
        };

        txService = new TransactionService(mockAccountRepo, mockTxRepo);
    }

    @Test
    void testDeposit_Success() {
        Account acc = new SavingsAccount("ACC-1", "Owner", 1000.0, "hash", LocalDateTime.now(), "ACTIVE");
        txService.deposit(acc, 500.0);

        assertEquals(1500.0, acc.getBalance());
        assertEquals(1, txLog.size());
        assertEquals("DEPOSIT", txLog.get(0).getType());
        assertEquals(500.0, txLog.get(0).getAmount());
    }

    @Test
    void testDeposit_NegativeAmount() {
        Account acc = new SavingsAccount("ACC-1", "Owner", 1000.0, "hash", LocalDateTime.now(), "ACTIVE");
        assertThrows(InvalidTransactionException.class, () -> txService.deposit(acc, -100.0));
    }

    @Test
    void testWithdraw_Success() {
        Account acc = new SavingsAccount("ACC-1", "Owner", 2000.0, "hash", LocalDateTime.now(), "ACTIVE");
        txService.withdraw(acc, 500.0);

        assertEquals(1500.0, acc.getBalance());
        assertEquals(1, txLog.size());
        assertEquals("WITHDRAW", txLog.get(0).getType());
    }

    @Test
    void testWithdraw_InsufficientFunds() {
        Account acc = new SavingsAccount("ACC-1", "Owner", 1000.0, "hash", LocalDateTime.now(), "ACTIVE");
        // SavingsAccount minimum is 1000, so any withdrawal should fail here
        assertThrows(InvalidTransactionException.class, () -> txService.withdraw(acc, 100.0));
    }
}
