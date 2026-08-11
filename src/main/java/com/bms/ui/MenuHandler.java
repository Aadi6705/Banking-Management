package com.bms.ui;

import com.bms.exception.AccountValidationException;
import com.bms.model.Account;
import com.bms.service.AccountService;
import com.bms.service.AuthService;

import java.util.Scanner;

public class MenuHandler {
    private final AccountService accountService;
    private final AuthService authService;
    private final Scanner scanner;

    public MenuHandler(AccountService accountService, AuthService authService) {
        this.accountService = accountService;
        this.authService = authService;
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
                    handleLogin();
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

    private void handleLogin() {
        ConsolePrinter.printHeader("LOGIN");
        
        System.out.print(" Enter Account Number: ");
        String accountNumber = scanner.nextLine().trim();
        
        System.out.print(" Enter 4-digit PIN: ");
        String pin = scanner.nextLine().trim();
        
        try {
            Account loggedInAccount = authService.login(accountNumber, pin);
            ConsolePrinter.printSuccess("Welcome back, " + loggedInAccount.getOwnerName() + "!");
            showUserDashboard(loggedInAccount);
        } catch (AccountValidationException e) {
            ConsolePrinter.printError(e.getMessage());
        } catch (Exception e) {
            ConsolePrinter.printError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void showUserDashboard(Account account) {
        while (true) {
            ConsolePrinter.printHeader("USER DASHBOARD - " + account.getAccountNumber());
            System.out.println(" 1. Check Balance");
            System.out.println(" 2. Logout");
            ConsolePrinter.printSeparator();
            System.out.print(" Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    ConsolePrinter.printInfo("Current Balance: " + ConsolePrinter.formatCurrency(account.getBalance()));
                    break;
                case "2":
                    ConsolePrinter.printSuccess("Logged out successfully.");
                    return; // Returns to Main Menu
                default:
                    ConsolePrinter.printError("Invalid option. Please enter 1 or 2.");
            }
        }
    }
}
