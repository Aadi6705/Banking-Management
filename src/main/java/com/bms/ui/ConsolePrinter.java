package com.bms.ui;

/**
 * Utility for standardizing console output formatting per Design.md.
 */
public class ConsolePrinter {

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
}
