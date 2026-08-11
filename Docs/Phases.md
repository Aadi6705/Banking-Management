# Phases.md — Build Roadmap

The project is broken into incremental phases. Each phase should be fully working and committed to Git before moving to the next. Do not skip ahead.

---

## Phase 0 — Project Setup
**Goal:** A running skeleton with the right structure, nothing functional yet.
- Initialize Git repository, create `.gitignore` (Java/Maven template).
- Set up Maven project (`pom.xml`) with JDK 17, JUnit 5 dependency.
- Create the folder/package structure exactly as defined in Architecture.md.
- Create empty `Main.java` that prints a welcome banner.
- Push initial commit to GitHub.

**Done when:** Project builds and runs, printing a welcome message, with the correct folder structure committed.

---

## Phase 1 — Core Models & Account Creation
**Goal:** Define the domain model and support creating an account.
- Implement `Account` (abstract), `SavingsAccount`, `CurrentAccount`, `Customer`.
- Implement `IdGenerator` for unique account numbers.
- Implement `PasswordUtil` for PIN hashing.
- Implement `FileManager` basic read/write helpers.
- Implement `FileAccountRepository` (save/find/list accounts to/from `accounts.csv`).
- Implement `AccountService.createAccount(...)`.
- Console menu: option to create a new account, with input validation (name required, account type valid, PIN meets basic rules).
- Unit tests for account creation and validation.

**Done when:** A user can create a Savings or Current account via the console, it's saved to file, and reloading the app shows it still exists.

---

## Phase 2 — Authentication & Balance Inquiry
**Goal:** Users can log in and check their balance.
- Implement `AuthService.login(accountNumber, pin)`.
- Console menu: login flow (retry on failure, lock out after N attempts — optional stretch).
- Implement balance inquiry for the logged-in account.
- Unit tests for login success/failure cases.

**Done when:** A user can log in with correct credentials and view their balance; incorrect credentials are rejected with a clear message.

---

## Phase 3 — Transactions (Deposit & Withdraw)
**Goal:** Core money-movement operations with full validation.
- Implement `Transaction` model and `FileTransactionRepository` (append-only log to `transactions.csv`).
- Implement `TransactionService.deposit(...)` and `TransactionService.withdraw(...)` with:
  - Positive-amount validation.
  - Sufficient-balance / minimum-balance validation (per account type).
- Wire deposit/withdraw into the console menu for the logged-in user.
- Every transaction updates the account balance and appends a transaction record — both persisted.
- Unit tests: valid deposit, valid withdrawal, overdraft rejection, negative-amount rejection.

**Done when:** A logged-in user can deposit and withdraw funds; balances update correctly and persist; invalid operations are rejected with clear errors.

---

## Phase 4 — Transaction History & Reporting
**Goal:** Users (and admins) can review activity.
- Implement `TransactionService.getHistory(accountNumber)`.
- Console menu: view transaction history (formatted table: type, amount, timestamp, resulting balance).
- Admin utility: list all accounts, search by name/account number.
- Admin utility: basic bank-wide summary (total accounts, total balance).
- Unit tests for history retrieval and admin search.

**Done when:** A user can view their full transaction history; an admin can list/search all accounts and see a summary report.

---

## Phase 5 — Fund Transfer (Stretch)
**Goal:** Move money between two accounts atomically.
- Implement `TransactionService.transfer(fromAccount, toAccount, amount)`.
- Ensure both legs of the transfer succeed or neither does (no partial transfer).
- Console menu: transfer option for logged-in user.
- Unit tests: successful transfer, insufficient-funds rejection, invalid destination account rejection.

**Done when:** A user can transfer funds to another valid account, with both account balances and transaction logs updated consistently.

---

## Phase 6 — Polish, Testing & Documentation
**Goal:** Production-quality finish for submission/portfolio use.
- Full pass on error messages/UX in the console (clear prompts, formatted tables/menus).
- Expand unit test coverage across services (target: all Service Layer public methods).
- Write/finalize `README.md`: setup instructions, how to run, feature list, screenshots/sample console output.
- Code review pass against Rules.md (naming, layering, no leaked I/O in wrong layers).
- Tag a `v1.0` release on GitHub.

**Done when:** The project is fully working end-to-end, documented, tested, and tagged as a releasable v1.0.

---

## Phase 7 — Optional Future Enhancements (not required for v1)
Pick any of these only after Phase 6 is complete and stable:
- Swing/JavaFX GUI on top of the existing services.
- Migrate storage from CSV files to SQLite/MySQL via new repository implementations.
- Interest calculation for Savings accounts (scheduled/simulated).
- Overdraft support for Current accounts.
- Multi-branch or multi-currency support.
- REST API layer (Spring Boot) for a future web/mobile front end.

---

## How to Use This With an AI Coding Tool
- Tell the AI which phase you're on at the start of each session (or point it to `Memory.md`).
- Do not ask for multiple phases in one prompt — this leads to skipped validation and inconsistent state.
- After each phase is verified working, commit, then update `Memory.md` before starting the next phase.
