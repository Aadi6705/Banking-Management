package com.bms.repository.impl;

import com.bms.model.Account;
import com.bms.model.CurrentAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileAccountRepositoryTest {

    private Path tempFile;
    private FileAccountRepository repository;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = Files.createTempFile("test_accounts", ".csv");
        repository = new FileAccountRepository(tempFile.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testSaveAndFind() {
        Account acc = new CurrentAccount("ACC-999999", "Jane Doe", 500.0, "hashed", LocalDateTime.now(), "ACTIVE");
        repository.save(acc);

        Account loaded = repository.findByAccountNumber("ACC-999999");
        assertNotNull(loaded);
        assertEquals("Jane Doe", loaded.getOwnerName());
        assertEquals(500.0, loaded.getBalance());
        assertTrue(loaded instanceof CurrentAccount);
    }
}
