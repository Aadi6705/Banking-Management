# Banking Management System (BMS)

A robust, Object-Oriented Java fintech application for account management, transaction processing, and fund transfers. Built entirely with core Java libraries, featuring atomic file-based persistence (CSV) and a polished Command Line Interface (CLI).

## Features
- **Account Creation**: Supports both `SAVINGS` (requires minimum balance) and `CURRENT` accounts. Auto-generates unique `ACC-XXXXXX` identifiers.
- **Secure Authentication**: PINs are securely hashed using `SHA-256` and `Base64` encoding.
- **Transactions**: Process deposits and withdrawals with strict validation rules.
- **Fund Transfers**: Transfer money securely between two accounts with guaranteed multi-account atomicity.
- **Transaction History**: View a tabular layout of all past deposits, withdrawals, and transfers.
- **Admin Utilities**: Search for accounts across the bank and generate bank-wide capital summaries.
- **File-Based Persistence**: All data is saved to `accounts.csv` and `transactions.csv`. Writes are performed atomically to ensure no corrupted states if the application crashes mid-transaction.

## Technical Constraints
This project adheres to a strict set of architectural rules:
- **No databases**: Pure File I/O using CSV format.
- **No third-party libraries**: Only standard `java.*` utilities. No Spring, no Hibernate, no Maven dependencies for execution.
- **No swallowed exceptions**: All runtime errors fail-fast or bubble up cleanly to the UI using custom exceptions (`DataStorageException`, `AccountValidationException`, etc).

---

## How to Build and Run

Because this project relies strictly on core Java without external build tools, you do not need Maven or Gradle to run the application.

### Prerequisites
- JDK 8 or higher installed on your system.

### Compilation
From the root directory of the project, compile all Java source files into the `src/main/java` directory:
```bash
javac -cp src/main/java $(find src/main/java -name "*.java")
```
*(Note for Windows users: Replace `$(find src/main/java -name "*.java")` with a list of all java files, or use a bash emulator like Git Bash).*

### Execution
Run the `Main` class from the root directory:
```bash
java -cp src/main/java com.bms.Main
```

### Running the Tests
To run the JUnit tests, you would typically need JUnit on your classpath. However, since the core application is designed to be dependency-free, you can safely skip tests for basic execution. If you have an IDE (IntelliJ, Eclipse), you can run the `src/test/java` directory directly through the IDE's built-in test runner.

---

## Usage Guide

1. **Create an Account**: Select option 1, enter your name, account type (`SAVINGS` or `CURRENT`), an initial deposit (minimum ₹1000 for savings), and a 4-digit PIN. Keep your auto-generated `ACC-XXXXXX` account number safe!
2. **Login**: Select option 2 and enter your account number and PIN to access your personal dashboard.
3. **Admin Utilities**: While on the Main Menu, type `99` (hidden option) to access the Admin panel, where you can search for users or view a summary of the bank's total capital.

## Architecture
- `com.bms.model`: Domain objects (`Account`, `Transaction`).
- `com.bms.repository`: Data access interfaces and File I/O implementations.
- `com.bms.service`: Core business logic and validation rules (`AccountService`, `TransactionService`, `AuthService`).
- `com.bms.ui`: Presentation layer (`MenuHandler`, `ConsolePrinter`).
- `com.bms.exception`: Custom exception hierarchy for clean error propagation.
- `com.bms.util`: Helpers for hashing (`PasswordUtil`) and atomic file handling (`FileManager`).
