package com.bms.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handles basic file I/O operations for CSV storage.
 */
public class FileManager {

    /**
     * Reads all lines from a file, creating it if it doesn't exist.
     */
    public static List<String> readAllLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }
        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.toList());
        }
    }

    /**
     * Overwrites a file entirely with the given lines.
     * Uses atomic move for safety to prevent partial writes.
     */
    public static void writeAllLines(String filePath, List<String> lines) throws IOException {
        Path path = Paths.get(filePath);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        Path tempFile = Files.createTempFile(path.getParent(), "temp", ".csv");
        Files.write(tempFile, lines, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        Files.move(tempFile, path, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Appends a single line to a file, creating it if it doesn't exist.
     */
    public static void appendLine(String filePath, String line) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.createFile(path);
        }
        Files.write(path, (line + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
    }
}
