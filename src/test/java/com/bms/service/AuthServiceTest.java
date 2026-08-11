package com.bms.service;

import com.bms.exception.AccountValidationException;
import com.bms.model.Account;
import com.bms.model.CurrentAccount;
import com.bms.repository.AccountRepository;
import com.bms.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    private AccountRepository mockRepo;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        String testPinHash = PasswordUtil.hashPin("1234");
        Account testAccount = new CurrentAccount("ACC-111111", "John Doe", 500.0, testPinHash, LocalDateTime.now(), "ACTIVE");
        
        mockRepo = new AccountRepository() {
            @Override
            public void save(Account account) {}
            @Override
            public Account findByAccountNumber(String accountNumber) {
                if ("ACC-111111".equals(accountNumber)) return testAccount;
                return null;
            }
            @Override
            public List<Account> findAll() { return Collections.singletonList(testAccount); }
        };

        authService = new AuthService(mockRepo);
    }

    @Test
    void testLogin_Success() {
        Account acc = authService.login("ACC-111111", "1234");
        assertNotNull(acc);
        assertEquals("ACC-111111", acc.getAccountNumber());
    }

    @Test
    void testLogin_WrongPin() {
        assertThrows(AccountValidationException.class, () -> {
            authService.login("ACC-111111", "9999");
        });
    }

    @Test
    void testLogin_UnknownAccount() {
        assertThrows(AccountValidationException.class, () -> {
            authService.login("ACC-999999", "1234");
        });
    }
}
