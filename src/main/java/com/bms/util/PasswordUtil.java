package com.bms.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility for hashing and verifying PINs.
 */
public class PasswordUtil {

    /**
     * Hashes a plain text PIN using SHA-256.
     * @param plainTextPin The plain text PIN
     * @return The Base64 encoded hash
     */
    public static String hashPin(String plainTextPin) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plainTextPin.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error initializing SHA-256 hashing algorithm", e);
        }
    }

    /**
     * Verifies a plain text PIN against a hash.
     * @param plainTextPin The plain text PIN
     * @param hash The expected hash
     * @return true if the PIN matches the hash, false otherwise
     */
    public static boolean verifyPin(String plainTextPin, String hash) {
        String hashedInput = hashPin(plainTextPin);
        return hashedInput.equals(hash);
    }
}
