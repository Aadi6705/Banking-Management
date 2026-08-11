# Memory.md — Progress Tracker (Living Document)

> **Note:** This file is not meant to be filled in yet. Create/start updating it once coding actually begins (Phase 0). Its job is to give any AI tool (or your future self) instant context on where the project stands, without needing to re-read the whole codebase or guess at what's done.

**How to use this file:**
- Update it at the **end of every coding session**, or immediately after completing a phase/feature.
- Keep entries short and factual — status, not narrative.
- Always update "Current Phase" and "Next Steps" — these are the two things a new session needs first.
- Never let this file fall out of sync with actual code — if something described here isn't really done, fix the file, not the story.

---

## Project Snapshot
- **Project:** Banking Management System (Java, OOP, File Handling)
- **Current Phase:** Phase 4 — Transaction History & Reporting
- **Last Updated:** 2026-08-11
- **Repo:** Local Git Repository (Phase 3 committed)

---

## Completed Phases
_(List phases fully done, with commit reference if useful.)_
- [x] Phase 0 — Project Setup
- [x] Phase 1 — Core Models & Account Creation
- [x] Phase 2 — Authentication & Balance Inquiry
- [x] Phase 3 — Transactions (Deposit & Withdraw)
- [ ] Phase 4 — Transaction History & Reporting
- [ ] Phase 5 — Fund Transfer
- [ ] Phase 6 — Polish, Testing & Documentation

---

## Current State of the Code
_(A quick factual list — what exists and works right now.)_
- **Models & Utils:** Complete (`Account`, `Customer`, `Transaction`, `IdGenerator`, `PasswordUtil`, `FileManager`).
- **Repository:** `FileAccountRepository` and `FileTransactionRepository` handle CSV data securely.
- **Services:** `AccountService`, `AuthService`, and `TransactionService` implement business rules and data atomicity.
- **UI:** `MenuHandler` manages the Main Menu and User Dashboard.
- **Features working:** Account Creation, Login, Balance Inquiry, Deposits, and Withdrawals.

---

## Decisions Made Along the Way
_(Anything that deviated from or clarified the original docs — so it's not re-litigated or re-guessed later.)_
- `mvn` was not found locally, so verified `Main.java` directly with `javac`.
- **Minimum Balance (Savings):** Hardcoded to ₹1000.00 for v1.
- **PIN Complexity:** Fixed to exactly 4 numeric digits.
- **Corrupted Data:** Fail-fast on application load to protect data integrity.
- **Lockout:** Left out for v1 (unlimited retries) to keep implementation simple.
- **Session Management:** Kept locally in `MenuHandler`'s `showUserDashboard` loop.
- **Transaction IDs:** Using standard Java UUIDs.
- **Persistence Atomicity:** `TransactionService` appends to the transaction log *before* updating the account file to prevent state divergence in case of crashes.

---

## Next Steps
_(The immediate next 1–3 tasks — what a new session should pick up first.)_
1. Implement `TransactionService.getHistory(accountNumber)`.
2. Add "View Transaction History" to the User Dashboard and format output as a table.
3. Add Admin Utility functionality (list all accounts, search by name/ID, bank-wide summary report) to a new Admin Menu.

---

## Open Questions
_(Anything unresolved that needs a decision before proceeding.)_
- How should we access the "Admin Utility"? Should we add a hardcoded "Admin Login" to the Main Menu, or just a hidden option?
