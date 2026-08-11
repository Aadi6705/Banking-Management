package com.bms.util;

import java.util.Random;

/**
 * Utility for generating unique account numbers.
 */
public class IdGenerator {
    private static final Random random = new Random();

    /**
     * Generates a 6-digit account number prefixed with "ACC-".
     * E.g., ACC-123456
     * Note: Uniqueness must be verified against the repository by the caller.
     */
    public static String generateAccountNumber() {
        int number = 100000 + random.nextInt(900000);
        return "ACC-" + number;
    }
}
