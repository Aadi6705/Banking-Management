package com.bms.repository;

import com.bms.model.Account;
import java.util.List;

/**
 * Interface for Account data persistence.
 */
public interface AccountRepository {
    void save(Account account);
    Account findByAccountNumber(String accountNumber);
    List<Account> findAll();
}
