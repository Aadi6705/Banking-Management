package com.bms.ui;

import com.bms.exception.AccountValidationException;
import com.bms.exception.InvalidTransactionException;
import com.bms.model.Account;
import com.bms.service.AccountService;
import com.bms.service.AdminService;
import com.bms.service.AuthService;
import com.bms.service.TransactionService;

import java.util.Scanner;

public class MenuHandler {
    private final AccountService accountService;
    private final AuthService authService;
    private final TransactionService transactionService;
    private final AdminService adminService;
    private final Scanner scanner;

    public MenuHandler(AccountService accountService, AuthService authService, TransactionService transactionService, AdminService adminService) {
        this.accountService = accountService;
        this.authService = authService;
        this.transactionService = transactionService;
        this.adminService = adminService;
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
                case "99":
                    handleAdminMenu();
                    break;
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
            System.out.println(" 2. Deposit");
            System.out.println(" 3. Withdraw");
            System.out.println(" 4. Transaction History");
            System.out.println(" 5. Transfer Funds");
            System.out.println(" 6. Logout");
            ConsolePrinter.printSeparator();
            System.out.print(" Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    ConsolePrinter.printInfo("Current Balance: " + ConsolePrinter.formatCurrency(account.getBalance()));
                    break;
                case "2":
                    handleDeposit(account);
                    break;
                case "3":
                    handleWithdraw(account);
                    break;
                case "4":
                    ConsolePrinter.printHeader("TRANSACTION HISTORY");
                    ConsolePrinter.printTransactionHistory(transactionService.getHistory(account.getAccountNumber()));
                    break;
                case "5":
                    handleTransfer(account);
                    break;
                case "6":
                    ConsolePrinter.printSuccess("Logged out successfully.");
                    return; // Returns to Main Menu
                default:
                    ConsolePrinter.printError("Invalid option. Please enter 1-6.");
            }
        }
    }

    private void handleDeposit(Account account) {
        System.out.print(" Enter amount to deposit: ₹");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            transactionService.deposit(account, amount);
            ConsolePrinter.printSuccess("Deposit of " + ConsolePrinter.formatCurrency(amount) + " completed. New balance: " + ConsolePrinter.formatCurrency(account.getBalance()));
        } catch (NumberFormatException e) {
            ConsolePrinter.printError("Invalid amount format.");
        } catch (InvalidTransactionException e) {
            ConsolePrinter.printError(e.getMessage());
        } catch (Exception e) {
            ConsolePrinter.printError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleWithdraw(Account account) {
        System.out.print(" Enter amount to withdraw: ₹");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            transactionService.withdraw(account, amount);
            ConsolePrinter.printSuccess("Withdrawal of " + ConsolePrinter.formatCurrency(amount) + " completed. New balance: " + ConsolePrinter.formatCurrency(account.getBalance()));
        } catch (NumberFormatException e) {
            ConsolePrinter.printError("Invalid amount format.");
        } catch (InvalidTransactionException e) {
            ConsolePrinter.printError(e.getMessage());
        } catch (Exception e) {
            ConsolePrinter.printError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleTransfer(Account account) {
        System.out.print(" Enter Target Account Number: ");
        String targetAcc = scanner.nextLine().trim();
        
        System.out.print(" Enter amount to transfer: ₹");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            transactionService.transfer(account, targetAcc, amount);
            ConsolePrinter.printSuccess("Transfer of " + ConsolePrinter.formatCurrency(amount) + " to " + targetAcc + " completed.");
            ConsolePrinter.printInfo("New balance: " + ConsolePrinter.formatCurrency(account.getBalance()));
        } catch (NumberFormatException e) {
            ConsolePrinter.printError("Invalid amount format.");
        } catch (InvalidTransactionException e) {
            ConsolePrinter.printError(e.getMessage());
        } catch (Exception e) {
            ConsolePrinter.printError("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void handleAdminMenu() {
        while (true) {
            ConsolePrinter.printHeader("ADMIN UTILITIES");
            System.out.println(" 1. List all accounts");
            System.out.println(" 2. Search accounts");
            System.out.println(" 3. Bank-wide summary report");
            System.out.println(" 4. Return to Main Menu");
            ConsolePrinter.printSeparator();
            System.out.print(" Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    ConsolePrinter.printHeader("ALL ACCOUNTS");
                    ConsolePrinter.printAccountList(adminService.getAllAccounts());
                    break;
                case "2":
                    System.out.print(" Enter search query (Name or Account No): ");
                    String query = scanner.nextLine().trim();
                    ConsolePrinter.printHeader("SEARCH RESULTS");
                    ConsolePrinter.printAccountList(adminService.searchAccounts(query));
                    break;
                case "3":
                    ConsolePrinter.printHeader("BANK SUMMARY REPORT");
                    System.out.println(adminService.getBankSummary());
                    ConsolePrinter.printSeparator();
                    break;
                case "4":
                    return;
                default:
                    ConsolePrinter.printError("Invalid option. Please enter 1, 2, 3, or 4.");
            }
        }
    }
}
