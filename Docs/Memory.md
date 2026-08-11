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
- **Current Phase:** Phase 1 — Core Models & Account Creation
- **Last Updated:** 2026-08-11
- **Repo:** Local Git Repository (Initialized)

---

## Completed Phases
_(List phases fully done, with commit reference if useful.)_
- [x] Phase 0 — Project Setup
- [ ] Phase 1 — Core Models & Account Creation
- [ ] Phase 2 — Authentication & Balance Inquiry
- [ ] Phase 3 — Transactions (Deposit & Withdraw)
- [ ] Phase 4 — Transaction History & Reporting
- [ ] Phase 5 — Fund Transfer
- [ ] Phase 6 — Polish, Testing & Documentation

---

## Current State of the Code
_(A quick factual list — what exists and works right now.)_
- Project scaffolding completed (POM, Gitignore, Directories).
- `Main.java` exists and prints the welcome banner successfully.

---

## Decisions Made Along the Way
_(Anything that deviated from or clarified the original docs — so it's not re-litigated or re-guessed later.)_
- `mvn` was not found locally, so verified `Main.java` directly with `javac`. The `pom.xml` is still included for standard tooling compatibility.

---

## Next Steps
_(The immediate next 1–3 tasks — what a new session should pick up first.)_
1. Implement `Account` (abstract), `SavingsAccount`, `CurrentAccount`, `Customer` models.
2. Implement `IdGenerator` for unique account numbers.
3. Implement `PasswordUtil` for PIN hashing.

---

## Open Questions
_(Anything unresolved that needs a decision before proceeding.)_
- None at the moment.
