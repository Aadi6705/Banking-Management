package com.bms;

import com.bms.repository.AccountRepository;
import com.bms.repository.TransactionRepository;
import com.bms.repository.impl.FileAccountRepository;
import com.bms.repository.impl.FileTransactionRepository;
import com.bms.service.AccountService;
import com.bms.service.AuthService;
import com.bms.service.TransactionService;
import com.bms.ui.MenuHandler;

public class Main {
    public static void main(String[] args) {
        String accountsFilePath = "data/accounts.csv";
        String transactionsFilePath = "data/transactions.csv";
        
        AccountRepository accountRepository = new FileAccountRepository(accountsFilePath);
        TransactionRepository transactionRepository = new FileTransactionRepository(transactionsFilePath);
        
        AccountService accountService = new AccountService(accountRepository);
        AuthService authService = new AuthService(accountRepository);
        TransactionService transactionService = new TransactionService(accountRepository, transactionRepository);
        
        MenuHandler menuHandler = new MenuHandler(accountService, authService, transactionService);

        menuHandler.start();
    }
}
