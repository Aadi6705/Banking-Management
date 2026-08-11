package com.bms.ui;

import com.bms.model.Account;
import com.bms.model.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utility for standardizing console output formatting per Design.md.
 */
public class ConsolePrinter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void printBanner() {
        System.out.println("==================================================");
        System.out.println("        BANKING MANAGEMENT SYSTEM (BMS)");
        System.out.println("==================================================");
    }

    public static void printHeader(String title) {
        System.out.println("--------------------------------------------------");
        System.out.println(" " + title.toUpperCase());
        System.out.println("--------------------------------------------------");
    }

    public static void printSeparator() {
        System.out.println("--------------------------------------------------");
    }

    public static void printSuccess(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    public static void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void printWarning(String message) {
        System.out.println("[WARNING] " + message);
    }

    public static String formatCurrency(double amount) {
        return String.format("₹%,.2f", amount);
    }

    public static void printTransactionHistory(List<Transaction> transactions) {
        printSeparator();
        System.out.printf(" %-11s %-13s %-20s %-15s%n", "TYPE", "AMOUNT", "DATE/TIME", "BALANCE");
        printSeparator();
        
        if (transactions.isEmpty()) {
            System.out.println(" No transactions found.");
        } else {
            for (Transaction tx : transactions) {
                System.out.printf(" %-11s %-13s %-20s %-15s%n", 
                    tx.getType(), 
                    formatCurrency(tx.getAmount()), 
                    tx.getTimestamp().format(DATE_FORMATTER), 
                    formatCurrency(tx.getBalanceAfter())
                );
            }
        }
        printSeparator();
    }

    public static void printAccountList(List<Account> accounts) {
        printSeparator();
        System.out.printf(" %-12s %-20s %-10s %-15s%n", "ACCOUNT NO", "OWNER", "TYPE", "BALANCE");
        printSeparator();
        
        if (accounts.isEmpty()) {
            System.out.println(" No accounts found.");
        } else {
            for (Account acc : accounts) {
                String type = acc.getClass().getSimpleName().replace("Account", "").toUpperCase();
                System.out.printf(" %-12s %-20s %-10s %-15s%n", 
                    acc.getAccountNumber(), 
                    // truncate long names
                    acc.getOwnerName().length() > 18 ? acc.getOwnerName().substring(0, 15) + "..." : acc.getOwnerName(),
                    type,
                    formatCurrency(acc.getBalance())
                );
            }
        }
        printSeparator();
    }
}
