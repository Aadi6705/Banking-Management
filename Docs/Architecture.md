# Architecture.md — System Architecture & Technical Design

## 1. Technical Stack

| Layer | Technology |
|---|---|
| Language | Java (JDK 17 LTS) |
| Paradigm | Object-Oriented Programming |
| Data Storage | File Handling (structured text/CSV or serialized objects) — see §5 |
| Build Tool | Maven (preferred) or plain `javac`/`.jar` if kept simple |
| Version Control | Git + GitHub |
| UI | Console-based (CLI) for v1; optional Java Swing UI in a later phase (see Phases.md) |
| Testing | JUnit 5 |

---

## 2. High-Level Architecture

The system follows a **layered architecture** to keep concerns separated and the codebase modular:

```
┌─────────────────────────────┐
│      Presentation Layer      │  ← Console UI / Menu system (Main, MenuHandler)
├─────────────────────────────┤
│        Service Layer         │  ← Business logic (AccountService, TransactionService, AuthService)
├─────────────────────────────┤
│         Model Layer          │  ← Domain objects (Account, SavingsAccount, CurrentAccount, Transaction, Customer)
├─────────────────────────────┤
│       Persistence Layer      │  ← File I/O (FileManager, AccountRepository, TransactionRepository)
└─────────────────────────────┘
```

**Flow of a typical operation (e.g., Withdraw):**
1. User selects "Withdraw" from the console menu (Presentation Layer).
2. `MenuHandler` collects input and calls `TransactionService.withdraw(accountNumber, amount)`.
3. `TransactionService` validates the request (amount > 0, account exists, sufficient balance) using `ValidationUtil`.
4. On success, it updates the `Account` object's balance and creates a `Transaction` record.
5. `TransactionService` calls `AccountRepository.save(account)` and `TransactionRepository.append(transaction)` to persist changes.
6. Result (success/failure + message) is returned up to the Presentation Layer, which displays it to the user.

This ensures the Presentation Layer never touches files directly, and the Model Layer never touches I/O directly — each layer only talks to the one directly below it.

---

## 3. Folder & File Structure

```
banking-management-system/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── bms/
│                   ├── Main.java                     # Entry point
│                   ├── model/
│                   │   ├── Account.java               # Abstract base class
│                   │   ├── SavingsAccount.java        # Extends Account
│                   │   ├── CurrentAccount.java        # Extends Account
│                   │   ├── Customer.java
│                   │   └── Transaction.java
│                   ├── service/
│                   │   ├── AccountService.java        # Create/close/find accounts
│                   │   ├── TransactionService.java    # Deposit/withdraw/transfer/history
│                   │   └── AuthService.java           # Login, PIN verification
│                   ├── repository/
│                   │   ├── AccountRepository.java     # Interface
│                   │   ├── TransactionRepository.java # Interface
│                   │   └── impl/
│                   │       ├── FileAccountRepository.java
│                   │       └── FileTransactionRepository.java
│                   ├── ui/
│                   │   ├── MenuHandler.java           # Console menu & input loop
│                   │   └── ConsolePrinter.java        # Formatted output helpers
│                   ├── util/
│                   │   ├── ValidationUtil.java
│                   │   ├── IdGenerator.java           # Unique account number generation
│                   │   ├── PasswordUtil.java          # Hashing PINs/passwords
│                   │   └── FileManager.java           # Low-level file read/write helpers
│                   └── exception/
│                       ├── InsufficientFundsException.java
│                       ├── AccountNotFoundException.java
│                       └── InvalidTransactionException.java
│   └── test/
│       └── java/com/bms/                              # JUnit tests mirroring main structure
├── data/
│   ├── accounts.dat        # or accounts.csv depending on chosen format
│   └── transactions.dat
├── docs/
│   ├── PRD.md
│   ├── Architecture.md
│   ├── Rules.md
│   ├── Phases.md
│   ├── Design.md
│   └── Memory.md
├── pom.xml                 # If using Maven
├── .gitignore
└── README.md
```

---

## 4. Core Classes & OOP Design

- **`Account` (abstract class):** Holds shared fields (accountNumber, owner, balance, PIN hash, createdDate) and shared behavior (deposit, withdraw base logic). Declares abstract method `applyAccountRules()` for subclass-specific rules (e.g., minimum balance).
- **`SavingsAccount extends Account`:** Enforces minimum balance, may apply interest calculation logic (future phase).
- **`CurrentAccount extends Account`:** No minimum balance requirement; may support overdraft in a future phase.
- **`Customer`:** Holds customer identity info (name, contact, ID), linked to one or more accounts.
- **`Transaction`:** Immutable record of a single operation (type, amount, timestamp, resulting balance, related account number).
- **Interfaces (`AccountRepository`, `TransactionRepository`):** Abstract the persistence mechanism so file-based storage can later be swapped for a database without touching service logic (Dependency Inversion).
- **Custom Exceptions:** Used instead of generic exceptions to make error handling explicit and meaningful (see Rules.md for error-handling policy).

**OOP principles applied:**
- **Encapsulation:** Account balance is private with controlled access via methods (no direct field mutation).
- **Inheritance:** `SavingsAccount` / `CurrentAccount` extend `Account`.
- **Polymorphism:** Service layer works with `Account` references; actual behavior resolved at runtime per subclass.
- **Abstraction:** Repository interfaces hide storage implementation details from the service layer.

---

## 5. Data Storage Design

v1 uses **file handling** as required by the project brief. Two viable approaches — pick one and stay consistent:

**Option A — CSV/plain text (recommended for readability & simplicity):**
```
accounts.csv
accountNumber,ownerName,accountType,balance,pinHash,createdDate,status

transactions.csv
transactionId,accountNumber,type,amount,timestamp,balanceAfter
```

**Option B — Java Serialization (`.dat` files via `ObjectOutputStream`):**
Simpler to write to/from Java objects directly, but less human-readable and harder to debug/inspect.

**Recommendation:** Start with CSV (Option A) — it's easier to inspect, debug, and later migrate to a database (columns map directly to table schema).

**Write strategy:** Every mutating operation (deposit, withdraw, create account) writes through to disk immediately (no in-memory-only state that could be lost). Use buffered writers for performance if the account count grows.

---

## 6. Error & Exception Flow

- Validation errors (e.g., negative amount) are caught at the Service Layer and surfaced as custom exceptions.
- The Presentation Layer catches these exceptions and prints a user-friendly message — it never lets a raw stack trace reach the console user.
- File I/O errors are logged and surfaced as a generic "system error" to the user, without crashing the app.

(Full policy in Rules.md §3.)

---

## 7. Future Extensibility (not in v1, but architecture should not block these)

- Swap file storage for a relational database (SQLite/MySQL) by implementing new `AccountRepository`/`TransactionRepository` classes.
- Add a Swing or JavaFX GUI on top of the existing Service Layer without changing business logic.
- Add interest calculation, loans, or multi-branch support as new service classes.
- Add a REST API layer (Spring Boot) if the project evolves toward a web app.
