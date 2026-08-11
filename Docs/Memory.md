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
- **Current Phase:** _(update — e.g., "Phase 1 — Core Models & Account Creation")_
- **Last Updated:** _(date)_
- **Repo:** _(GitHub URL)_

---

## Completed Phases
_(List phases fully done, with commit reference if useful.)_
- [ ] Phase 0 — Project Setup
- [ ] Phase 1 — Core Models & Account Creation
- [ ] Phase 2 — Authentication & Balance Inquiry
- [ ] Phase 3 — Transactions (Deposit & Withdraw)
- [ ] Phase 4 — Transaction History & Reporting
- [ ] Phase 5 — Fund Transfer
- [ ] Phase 6 — Polish, Testing & Documentation

---

## Current State of the Code
_(A quick factual list — what exists and works right now.)_
- Classes implemented: _e.g., Account, SavingsAccount, CurrentAccount_
- Services implemented: _e.g., AccountService.createAccount()_
- Persistence: _e.g., accounts.csv read/write working; transactions.csv not started_
- Known working features: _e.g., account creation via console, login_
- Known bugs / rough edges: _e.g., no duplicate-account-number check yet_

---

## Decisions Made Along the Way
_(Anything that deviated from or clarified the original docs — so it's not re-litigated or re-guessed later.)_
- _e.g., "Chose CSV over serialization for accounts.csv, per Architecture.md §5 recommendation."_

---

## Next Steps
_(The immediate next 1–3 tasks — what a new session should pick up first.)_
1. _e.g., Implement IdGenerator for unique account numbers_
2. _e.g., Add unit tests for AccountService.createAccount()_
3. _e.g., Start Phase 2 login flow_

---

## Open Questions
_(Anything unresolved that needs a decision before proceeding.)_
- _e.g., Should minimum balance for Savings accounts be configurable or hardcoded for v1?_
