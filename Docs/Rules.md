# Rules.md — AI Coding Boundaries & Conventions

These rules govern how any AI assistant (or contributor) should behave while building this project. They exist to keep the codebase consistent, secure, and aligned with Architecture.md, even across multiple sessions/tools.

---

## 1. Language, Version & Libraries

- **Language:** Java only. JDK version: **17 (LTS)**. Do not use preview/incubator features.
- **Allowed libraries:**
  - `java.util`, `java.io`, `java.nio.file`, `java.time` (standard library — preferred for everything possible).
  - `JUnit 5` for testing.
  - `java.security.MessageDigest` (SHA-256) or `jBCrypt` for PIN/password hashing — never a custom/home-rolled crypto algorithm.
- **Avoid unless explicitly approved:**
  - No Spring/Spring Boot in v1 — this is a plain Java OOP project, not a web framework demo.
  - No ORM (Hibernate/JPA) in v1 — file handling is a stated requirement.
  - No third-party JSON/CSV libraries unless parsing genuinely becomes unmanageable by hand — prefer standard `java.io`/`java.nio` for the learning/portfolio value.
  - No GUI frameworks (Swing/JavaFX) until the phase explicitly calls for it (see Phases.md).
- Any new dependency must be added to `pom.xml` and justified in a commit message — never added silently.

---

## 2. Coding Conventions

- Follow standard Java naming conventions: `PascalCase` for classes, `camelCase` for methods/variables, `UPPER_SNAKE_CASE` for constants.
- One public class per file; file name matches class name.
- Keep methods short and single-purpose (prefer < 30 lines; refactor if a method is trying to do too much).
- No business logic in the Presentation Layer (`ui/` package) — it only collects input and displays output.
- No direct file I/O calls outside the `repository`/`util` (`FileManager`) layers.
- Use meaningful variable names — no `x`, `temp`, `data1` in committed code.
- Every public class and non-trivial method gets a short Javadoc comment explaining purpose, not just restating the signature.

---

## 3. Error Handling Policy

- Use **custom exceptions** (`InsufficientFundsException`, `AccountNotFoundException`, `InvalidTransactionException`, etc.) for expected business-rule failures — never let these be generic `Exception` or `RuntimeException` without a specific subclass.
- **Never** swallow exceptions silently (no empty `catch` blocks). At minimum, log the error.
- **Never** let a raw stack trace print to the end user in the console UI — catch at the boundary and show a clear, human-readable message.
- File I/O failures must not crash the application; fail gracefully and inform the user that an operation could not be completed.
- Input validation happens **before** any state mutation — never partially apply a transaction and then fail.
- All monetary values are validated as positive, non-zero, and within any relevant limits (e.g., minimum balance) before being applied.

---

## 4. Data Integrity Rules

- Balance must **never** go negative under normal operation (except explicitly designed overdraft logic, if/when added).
- Every successful transaction must be both applied to the in-memory account object **and** persisted to disk before being confirmed to the user. If persistence fails, the operation is not considered successful.
- Account numbers must be unique — check for collisions on generation.
- PINs/passwords are **never** stored or logged in plaintext, anywhere (files, console output, error messages, or commit history).
- Do not log sensitive customer data (full PIN, raw password) even in debug output.

---

## 5. Git & GitHub Conventions

- Commit early and often — one logical change per commit, not one giant commit per phase.
- Commit message format: `<type>: <short description>` — e.g., `feat: add withdraw validation`, `fix: prevent negative balance on concurrent withdraw`, `docs: update Architecture.md`.
- Types to use: `feat`, `fix`, `refactor`, `docs`, `test`, `chore`.
- Never commit the `data/` folder's real runtime data files if they contain anything beyond sample/test data — add relevant paths to `.gitignore` if this is a concern; otherwise keep sample seed data clearly marked as such.
- Never commit secrets, API keys, or credentials (not that this project should have any — but if config is added later, use environment variables or a gitignored config file).
- Branch per feature/phase where practical (e.g., `phase-2-transactions`); merge to `main` once a phase is stable.

---

## 6. What the AI Should Do

- Build strictly one phase at a time, per Phases.md — do not jump ahead and implement Phase 4 features while "just doing" Phase 2.
- Always update **Memory.md** after completing meaningful work in a session (see Memory.md instructions).
- Ask for clarification (or state an explicit assumption) when a requirement is ambiguous, rather than guessing silently.
- Write or update tests alongside new service-layer logic.
- Keep Architecture.md's folder structure and layering intact; if a genuinely better structure is discovered, propose the change explicitly rather than silently deviating.
- Explain non-obvious design decisions briefly in code comments or commit messages.

## 7. What the AI Should NOT Do

- Do not introduce a database, web framework, or GUI without it being scheduled in Phases.md.
- Do not rewrite/restructure already-completed phases without being asked — extend, don't churn.
- Do not store plaintext passwords/PINs, ever, "just for now" or "to make testing easier."
- Do not remove input validation to "make a demo work" — fix the underlying issue instead.
- Do not fabricate data, features, or completed work in Memory.md — it must reflect actual code state.
- Do not add dependencies not listed in §1 without flagging it clearly first.
