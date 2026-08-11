package com.bms.ui;

import com.bms.exception.AccountValidationException;
import com.bms.model.Account;
import com.bms.service.AccountService;

import java.util.Scanner;

public class MenuHandler {
    private final AccountService accountService;
    private final Scanner scanner;

    public MenuHandler(AccountService accountService) {
        this.accountService = accountService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        ConsolePrinter.printBanner();

        while (true) {
            ConsolePrinter.printHeader("MAIN MENU");
            System.out.println(" 1. Create Account");
            System.out.println(" 2. Login");
            System.out.println(" 3. Exit");
            ConsolePrinter.printSeparator();
            System.out.print(" Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleCreateAccount();
                    break;
                case "2":
                    ConsolePrinter.printInfo("Login feature coming in Phase 2.");
                    break;
                case "3":
                    ConsolePrinter.printInfo("Exiting System. Goodbye!");
                    return;
                default:
                    ConsolePrinter.printError("Invalid option. Please enter 1, 2, or 3.");
            }
        }
    }

    private void handleCreateAccount() {
        ConsolePrinter.printHeader("CREATE ACCOUNT");

        System.out.print(" Enter Owner Name: ");
        String name = scanner.nextLine().trim();

        System.out.print(" Enter Account Type (SAVINGS/CURRENT): ");
        String type = scanner.nextLine().trim().toUpperCase();

        System.out.print(" Enter Initial Deposit: ₹");
        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            ConsolePrinter.printError("Invalid amount format.");
            return;
        }

        System.out.print(" Create a 4-digit PIN: ");
        String pin = scanner.nextLine().trim();

        try {
            Account account = accountService.createAccount(name, type, pin, initialDeposit);
            ConsolePrinter.printSuccess("Account created successfully!");
            ConsolePrinter.printInfo("Your Account Number is: " + account.getAccountNumber());
        } catch (AccountValidationException | IllegalArgumentException | IllegalStateException e) {
            ConsolePrinter.printError(e.getMessage());
        } catch (Exception e) {
            ConsolePrinter.printError("An unexpected error occurred: " + e.getMessage());
        }
    }
}
