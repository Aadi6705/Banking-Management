package com.bms.service;

import com.bms.exception.AccountValidationException;
import com.bms.model.Account;
import com.bms.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private AccountRepository mockRepo;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        // Simple anonymous inner class to mock repository behavior
        mockRepo = new AccountRepository() {
            private List<Account> accounts = new ArrayList<>();
            @Override
            public void save(Account account) { accounts.add(account); }
            @Override
            public Account findByAccountNumber(String accountNumber) {
                return accounts.stream().filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
            }
            @Override
            public List<Account> findAll() { return accounts; }
        };
        accountService = new AccountService(mockRepo);
    }

    @Test
    void testCreateAccount_Success() {
        Account acc = accountService.createAccount("John Doe", "SAVINGS", "1234", 1000.0);
        assertNotNull(acc.getAccountNumber());
        assertEquals("John Doe", acc.getOwnerName());
        assertEquals(1000.0, acc.getBalance());
        assertNotNull(mockRepo.findByAccountNumber(acc.getAccountNumber()));
    }

    @Test
    void testCreateAccount_InvalidName() {
        assertThrows(AccountValidationException.class, () -> {
            accountService.createAccount("", "SAVINGS", "1234", 1000.0);
        });
    }

    @Test
    void testCreateAccount_InvalidPin() {
        assertThrows(AccountValidationException.class, () -> {
            accountService.createAccount("John Doe", "SAVINGS", "123", 1000.0);
        });
    }

    @Test
    void testCreateAccount_InvalidInitialDeposit() {
        assertThrows(AccountValidationException.class, () -> {
            accountService.createAccount("John Doe", "SAVINGS", "1234", 500.0);
        });
    }
}
