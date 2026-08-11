package com.bms;

import com.bms.repository.AccountRepository;
import com.bms.repository.impl.FileAccountRepository;
import com.bms.service.AccountService;
import com.bms.service.AuthService;
import com.bms.ui.MenuHandler;

public class Main {
    public static void main(String[] args) {
        String accountsFilePath = "data/accounts.csv";
        AccountRepository accountRepository = new FileAccountRepository(accountsFilePath);
        AccountService accountService = new AccountService(accountRepository);
        AuthService authService = new AuthService(accountRepository);
        MenuHandler menuHandler = new MenuHandler(accountService, authService);

        menuHandler.start();
    }
}
