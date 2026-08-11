package com.bms.service;

import com.bms.model.Account;
import com.bms.repository.AccountRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for administrative and reporting functions.
 */
public class AdminService {
    private final AccountRepository accountRepository;

    public AdminService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> searchAccounts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllAccounts();
        }
        
        String lowerQuery = query.toLowerCase().trim();
        return accountRepository.findAll().stream()
                .filter(a -> a.getAccountNumber().toLowerCase().contains(lowerQuery) || 
                             a.getOwnerName().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    public String getBankSummary() {
        List<Account> allAccounts = getAllAccounts();
        double totalBalance = allAccounts.stream()
                .mapToDouble(Account::getBalance)
                .sum();
        
        return String.format("Total Accounts: %d\nTotal Bank Balance: ₹%,.2f", allAccounts.size(), totalBalance);
    }
}
