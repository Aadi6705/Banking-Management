package com.bms;

import com.bms.repository.AccountRepository;
import com.bms.repository.impl.FileAccountRepository;
import com.bms.service.AccountService;
import com.bms.ui.MenuHandler;

public class Main {
    public static void main(String[] args) {
        String accountsFilePath = "data/accounts.csv";
        AccountRepository accountRepository = new FileAccountRepository(accountsFilePath);
        AccountService accountService = new AccountService(accountRepository);
        MenuHandler menuHandler = new MenuHandler(accountService);

        menuHandler.start();
    }
}
