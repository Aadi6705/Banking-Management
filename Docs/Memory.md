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
- **Current Phase:** Phase 2 — Authentication & Balance Inquiry
- **Last Updated:** 2026-08-11
- **Repo:** Local Git Repository (Phase 1 committed)

---

## Completed Phases
_(List phases fully done, with commit reference if useful.)_
- [x] Phase 0 — Project Setup
- [x] Phase 1 — Core Models & Account Creation
- [ ] Phase 2 — Authentication & Balance Inquiry
- [ ] Phase 3 — Transactions (Deposit & Withdraw)
- [ ] Phase 4 — Transaction History & Reporting
- [ ] Phase 5 — Fund Transfer
- [ ] Phase 6 — Polish, Testing & Documentation

---

## Current State of the Code
_(A quick factual list — what exists and works right now.)_
- **Models:** `Account` (abstract), `SavingsAccount`, `CurrentAccount`, `Customer` exist.
- **Utils:** `IdGenerator`, `PasswordUtil` (SHA-256), `FileManager` (CSV Atomic Writes) exist.
- **Repository:** `AccountRepository` and `FileAccountRepository` read/write `Account` data successfully.
- **Service:** `AccountService` handles validation, ID generation, PIN hashing, and saving.
- **UI:** `ConsolePrinter` standardizes formatting. `MenuHandler` provides an interactive "Create Account" CLI flow.
- **Tests:** `AccountServiceTest` and `FileAccountRepositoryTest` cover core behavior.
- **Known working features:** Account creation via console is verified, data persists to `data/accounts.csv`.

---

## Decisions Made Along the Way
_(Anything that deviated from or clarified the original docs — so it's not re-litigated or re-guessed later.)_
- `mvn` was not found locally, so verified `Main.java` directly with `javac`.
- **Minimum Balance (Savings):** Hardcoded to ₹1000.00 for v1.
- **PIN Complexity:** Fixed to exactly 4 numeric digits.
- **Corrupted Data:** Fail-fast on application load to protect data integrity.
- **PIN Hashing:** Standard Java `MessageDigest` (SHA-256).

---

## Next Steps
_(The immediate next 1–3 tasks — what a new session should pick up first.)_
1. Implement `AuthService.login(accountNumber, pin)`.
2. Implement login flow in `MenuHandler` with retry logic and session management.
3. Add balance inquiry logic for the logged-in session.

---

## Open Questions
_(Anything unresolved that needs a decision before proceeding.)_
- Should the "session" be managed locally inside `MenuHandler` by keeping the authenticated `Account` instance in a variable, or should there be a dedicated global Session manager class? (Recommending localized `MenuHandler` scope to keep it simple).
