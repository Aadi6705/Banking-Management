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
- **Current Phase:** COMPLETE
- **Last Updated:** 2026-08-11
- **Repo:** Local Git Repository (Phase 6 committed)

---

## Completed Phases
_(List phases fully done, with commit reference if useful.)_
- [x] Phase 0 — Project Setup
- [x] Phase 1 — Core Models & Account Creation
- [x] Phase 2 — Authentication & Balance Inquiry
- [x] Phase 3 — Transactions (Deposit & Withdraw)
- [x] Phase 4 — Transaction History & Reporting
- [x] Phase 5 — Fund Transfer
- [x] Phase 6 — Polish, Testing & Documentation

---

## Current State of the Code
_(A quick factual list — what exists and works right now.)_
- **Models & Utils:** Complete (`Account`, `Customer`, `Transaction`, `IdGenerator`, `PasswordUtil`, `FileManager`).
- **Repository:** `FileAccountRepository` and `FileTransactionRepository` handle CSV data securely, supporting bulk atomic `saveAll` overwrites.
- **Services:** `AccountService`, `AuthService`, `TransactionService`, and `AdminService`.
- **UI:** `MenuHandler` manages the Main Menu, User Dashboard, and Admin Utilities. `ConsolePrinter` handles tabular formatting.
- **Features working:** Account Creation, Login, Balance Inquiry, Deposits, Withdrawals, History Viewing, Admin Search, Admin Summary, Fund Transfers.
- **Documentation:** Full `README.md` included with standard Java execution instructions.

---

## Decisions Made Along the Way
_(Anything that deviated from or clarified the original docs — so it's not re-litigated or re-guessed later.)_
- `mvn` was not found locally, so verified `Main.java` directly with `javac`. The `README.md` instructs users to do the same.
- **Minimum Balance (Savings):** Hardcoded to ₹1000.00 for v1.
- **PIN Complexity:** Fixed to exactly 4 numeric digits.
- **Corrupted Data:** Fail-fast on application load to protect data integrity.
- **Lockout:** Left out for v1 (unlimited retries) to keep implementation simple.
- **Session Management:** Kept locally in `MenuHandler`'s `showUserDashboard` loop.
- **Transaction IDs:** Using standard Java UUIDs.
- **Persistence Atomicity:** Deposit/Withdraw append to the transaction log before updating accounts. Transfers utilize an in-memory load/modify/save-all pattern.
- **Admin Access:** Added a hidden `99` option in the Main Menu to access Admin utilities without full auth flow.
- **Error Handling:** All standard `RuntimeExceptions` for I/O and config were refactored into custom `DataStorageException` and `SystemConfigurationException` classes to strictly adhere to project rules.

---

## Next Steps
- The project is complete. Future iterations (like the Phase 7 GUI) will require a new PRD and Phase Plan.

---

## Open Questions
- None.
