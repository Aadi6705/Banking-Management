# PRD.md — Project Requirements Document

## 1. Project Overview

**Project Name:** Banking Management System (BMS)
**Category:** FinTech
**Type:** Java-based console/desktop application for account management and transaction processing

**Problem Statement:**
Small banks, credit unions, and educational institutions often need a lightweight, dependable system to manage customer accounts and transactions without the overhead of a full banking platform. This project delivers a modular, OOP-driven Java application that handles core banking operations reliably, with data integrity as a first-class concern.

**Elevator Pitch:**
A Java-based banking management system that lets staff (or account holders, depending on deployment) create accounts, deposit and withdraw funds, check balances, and view transaction history — all backed by validated, structured data storage and clean object-oriented design.

---

## 2. Goals & Objectives

- Provide a reliable system for core banking operations: account creation, deposits, withdrawals, balance inquiry, and transaction history.
- Demonstrate strong OOP design (encapsulation, inheritance, polymorphism, abstraction) applied to a real-world domain.
- Ensure data accuracy and consistency through validation at every transaction boundary.
- Persist data reliably using file handling (with a clear upgrade path to a database).
- Maintain a clean, modular codebase that is easy to extend (e.g., adding loans, interest calculation, multi-branch support).
- Track project history properly using Git/GitHub with meaningful commits.

### Non-Goals (out of scope for v1)
- Real money movement / integration with actual payment rails or banks.
- Multi-user concurrent access over a network (this is a single-user or single-machine app unless explicitly scoped later).
- Mobile or web front end (console-based or simple desktop UI only, see Design.md).
- Regulatory compliance (KYC/AML) — this is an academic/portfolio project, not a production financial product.

---

## 3. Target Users

| User Type | Description | Needs |
|---|---|---|
| **Bank Staff / Admin** | Operates the system to manage customer accounts | Create/close accounts, view all accounts, process transactions on behalf of customers |
| **Account Holder (Customer)** | End user with a bank account | Log in, check balance, deposit/withdraw, view transaction history |
| **Developer / Evaluator** | Reviews the codebase (recruiter, professor, contributor) | Clean code, clear structure, documented design decisions |

---

## 4. Core Features (v1 Scope)

### 4.1 Account Management
- Create a new account (Savings / Current) with owner details (name, ID, contact info).
- Auto-generate a unique account number.
- View account details.
- Close / deactivate an account.

### 4.2 Authentication
- Simple login using account number + PIN/password (hashed, not plaintext).
- Basic session handling (who is currently logged in).

### 4.3 Transactions
- **Deposit:** Add funds to an account; must be a positive amount.
- **Withdraw:** Remove funds; must not exceed available balance (respect minimum balance rules for Savings accounts if applicable).
- **Balance Inquiry:** Show current balance.
- **Transaction History:** List of past transactions per account (type, amount, timestamp, resulting balance).
- **Fund Transfer (stretch goal):** Move money between two accounts within the system.

### 4.4 Validation & Data Integrity
- Reject negative or zero amounts for deposits/withdrawals.
- Reject withdrawals exceeding balance (or minimum balance threshold).
- Validate account number format and existence before any operation.
- Prevent duplicate account numbers.
- All successful transactions are logged with a timestamp.

### 4.5 Data Persistence
- All account and transaction data is saved to files (see Architecture.md for format) so data survives program restarts.
- Data is reloaded on startup.

### 4.6 Admin Utilities (stretch goal)
- List all accounts.
- Search account by name or account number.
- Generate a simple summary report (total accounts, total balance across bank).

---

## 5. Functional Requirements

| ID | Requirement | Priority |
|---|---|---|
| FR-1 | System shall allow creation of a new account with validated input | Must |
| FR-2 | System shall generate a unique account number per account | Must |
| FR-3 | System shall support login via account number and PIN | Must |
| FR-4 | System shall allow deposits with amount validation | Must |
| FR-5 | System shall allow withdrawals with balance/minimum-balance validation | Must |
| FR-6 | System shall display current balance on request | Must |
| FR-7 | System shall record every transaction with type, amount, timestamp | Must |
| FR-8 | System shall display transaction history for an account | Must |
| FR-9 | System shall persist all data to disk and reload on startup | Must |
| FR-10 | System shall prevent invalid operations (negative amounts, non-existent accounts) with clear error messages | Must |
| FR-11 | System shall support fund transfer between two valid accounts | Should |
| FR-12 | System shall allow admin to list/search all accounts | Should |
| FR-13 | System shall support account closure | Could |
| FR-14 | System shall generate a basic report of bank-wide totals | Could |

---

## 6. Non-Functional Requirements

- **Reliability:** No transaction should ever leave the system in an inconsistent state (e.g., partial writes).
- **Maintainability:** Code organized by responsibility (models, services, storage, UI) following OOP principles.
- **Usability:** Clear prompts and error messages for console interaction.
- **Security (basic):** PINs/passwords are never stored in plaintext.
- **Performance:** Should comfortably handle hundreds to a few thousand accounts/transactions for demo purposes — not designed for high-concurrency production load.
- **Portability:** Runs on any machine with a standard JDK installed; no external services required for core functionality.

---

## 7. Success Criteria

- All "Must" functional requirements implemented and manually testable.
- No known bug allows balance to go negative or data to be lost on restart.
- Codebase demonstrates clear OOP structure (documented in Architecture.md).
- Project builds and runs from a clean clone via documented steps in README.
- Git history shows incremental, meaningful commits (not one giant commit).

---

## 8. Assumptions & Constraints

- Single-machine, single-user-at-a-time usage (no networking in v1).
- File-based storage is acceptable for v1; a database (e.g., SQLite/MySQL) is a possible future phase.
- No real currency/payment gateway integration.
- Java version: JDK 17+ (LTS) unless otherwise specified in Rules.md.
