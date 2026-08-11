package com.bms.service;

import com.bms.model.Account;
import com.bms.model.CurrentAccount;
import com.bms.model.SavingsAccount;
import com.bms.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {
    
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        AccountRepository mockRepo = new AccountRepository() {
            private final List<Account> accounts = Arrays.asList(
                new SavingsAccount("ACC-1", "Alice", 1000.0, "hash", LocalDateTime.now(), "ACTIVE"),
                new CurrentAccount("ACC-2", "Bob", 2000.0, "hash", LocalDateTime.now(), "ACTIVE")
            );
            @Override public void save(Account account) {}
            @Override public Account findByAccountNumber(String accountNumber) { return null; }
            @Override public List<Account> findAll() { return accounts; }
        };
        
        adminService = new AdminService(mockRepo);
    }

    @Test
    void testGetAllAccounts() {
        assertEquals(2, adminService.getAllAccounts().size());
    }

    @Test
    void testSearchAccounts() {
        List<Account> results = adminService.searchAccounts("alice");
        assertEquals(1, results.size());
        assertEquals("ACC-1", results.get(0).getAccountNumber());
        
        results = adminService.searchAccounts("ACC");
        assertEquals(2, results.size());
    }

    @Test
    void testGetBankSummary() {
        String summary = adminService.getBankSummary();
        assertTrue(summary.contains("Total Accounts: 2"));
        assertTrue(summary.contains("3,000.00"));
    }
}
