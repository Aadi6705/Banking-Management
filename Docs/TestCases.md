# TestCases.md — Manual Test Cases

Manual test plan for the Banking Management System, aligned with `PRD.md` (functional requirements), `Phases.md` (build order), and `Rules.md` (validation/error-handling policy). Run each phase's test cases once that phase is complete, before moving to the next phase.

**Legend:**
- **Priority:** Must / Should / Could (mirrors PRD.md priority levels)
- **Status:** Pass / Fail / Blocked / Not Run — fill in during test execution
- **Maps to:** the FR-ID from PRD.md this test verifies

---

## 1. Account Creation (Phase 1)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-01 | Create a valid Savings account | App running, main menu visible | 1. Select "Create Account"<br>2. Enter valid name, choose Savings, enter valid PIN | Account created; unique account number displayed; confirmation message shown | Must | FR-1, FR-2 | Pass |
| TC-02 | Create a valid Current account | App running | 1. Select "Create Account"<br>2. Enter valid name, choose Current, enter valid PIN | Account created; unique account number displayed | Must | FR-1, FR-2 | Pass |
| TC-03 | Reject account creation with empty name | App running | 1. Select "Create Account"<br>2. Leave name blank, submit | `[ERROR]` shown; account not created | Must | FR-1, FR-10 | Pass |
| TC-04 | Reject account creation with invalid account type | App running | 1. Select "Create Account"<br>2. Enter an option outside Savings/Current | `[ERROR]` shown; account not created | Must | FR-1, FR-10 | Pass |
| TC-05 | Reject weak/invalid PIN (per Rules.md format) | App running | 1. Select "Create Account"<br>2. Enter a PIN that fails format rules (e.g., too short) | `[ERROR]` shown; account not created | Should | FR-1, FR-10 | Pass |
| TC-06 | Two accounts never receive the same account number | Two accounts created in sequence | 1. Create account A<br>2. Create account B<br>3. Compare account numbers | Account numbers are unique | Must | FR-2, FR-10 | Pass |
| TC-07 | Newly created account has zero (or defined minimum) opening balance | Account just created | 1. Create account<br>2. Check balance | Balance matches defined opening balance (e.g., ₹0.00) | Should | FR-1 | Pass |

---

## 2. Authentication (Phase 2)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-08 | Login with correct account number and PIN | Account exists | 1. Select "Login"<br>2. Enter correct account number and PIN | Login succeeds; user lands on account menu | Must | FR-3 | Pass |
| TC-09 | Reject login with wrong PIN | Account exists | 1. Select "Login"<br>2. Enter correct account number, wrong PIN | `[ERROR]` shown; login denied; no account details leaked | Must | FR-3, FR-10 | Pass |
| TC-10 | Reject login with non-existent account number | App running | 1. Select "Login"<br>2. Enter an account number that doesn't exist | `[ERROR]` shown ("account not found" style message); login denied | Must | FR-3, FR-10 | Pass |
| TC-11 | PIN is never displayed or echoed in plaintext on screen or in logs | Login screen active | 1. Enter PIN during login<br>2. Check console output and any log/data files | PIN never appears in plaintext anywhere | Must | Rules.md §4 | Pass |
| TC-12 | Repeated failed logins are handled gracefully (no crash) | Account exists | 1. Attempt login with wrong PIN 5+ times in a row | App remains stable; each attempt shows a clear error; no crash | Should | FR-10 | Pass |

---

## 3. Deposits (Phase 3)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-13 | Deposit a valid positive amount | Logged in | 1. Select "Deposit"<br>2. Enter a valid positive amount | `[SUCCESS]` shown; balance increases by exact amount; transaction logged | Must | FR-4, FR-7 | Pass |
| TC-14 | Reject deposit of zero | Logged in | 1. Select "Deposit"<br>2. Enter 0 | `[ERROR]` shown; balance unchanged; no transaction logged | Must | FR-4, FR-10 | Pass |
| TC-15 | Reject deposit of a negative amount | Logged in | 1. Select "Deposit"<br>2. Enter a negative number | `[ERROR]` shown; balance unchanged | Must | FR-4, FR-10 | Pass |
| TC-16 | Reject non-numeric deposit input | Logged in | 1. Select "Deposit"<br>2. Enter text (e.g., "abc") | `[ERROR]` shown gracefully; app does not crash | Must | FR-10 | Pass |
| TC-17 | Deposit reflects correctly in stored file after restart | Deposit made | 1. Make a deposit<br>2. Close and restart the app<br>3. Log in and check balance | Balance matches the post-deposit amount | Must | FR-9 | Pass |

---

## 4. Withdrawals (Phase 3)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-18 | Withdraw a valid amount within balance | Logged in, sufficient balance | 1. Select "Withdraw"<br>2. Enter a valid amount ≤ balance | `[SUCCESS]` shown; balance decreases by exact amount; transaction logged | Must | FR-5, FR-7 | Pass |
| TC-19 | Reject withdrawal exceeding available balance | Logged in | 1. Select "Withdraw"<br>2. Enter an amount greater than current balance | `[ERROR]` shown ("insufficient funds"); balance unchanged | Must | FR-5, FR-10 | Pass |
| TC-20 | Reject withdrawal that breaches Savings minimum balance | Logged in to a Savings account | 1. Select "Withdraw"<br>2. Enter an amount that would drop balance below the defined minimum | `[ERROR]` shown; balance unchanged | Must | FR-5, FR-10 | Pass |
| TC-21 | Reject withdrawal of zero or negative amount | Logged in | 1. Select "Withdraw"<br>2. Enter 0 or a negative number | `[ERROR]` shown; balance unchanged | Must | FR-5, FR-10 | Pass |
| TC-22 | Withdrawal reflects correctly in stored file after restart | Withdrawal made | 1. Make a withdrawal<br>2. Close and restart the app<br>3. Log in and check balance | Balance matches the post-withdrawal amount | Must | FR-9 | Pass |

---

## 5. Balance Inquiry (Phase 2/3)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-23 | View current balance after login | Logged in | 1. Select "Check Balance" | Correct current balance displayed, formatted per Design.md (2 decimal places, currency symbol) | Must | FR-6 | Pass |
| TC-24 | Balance updates immediately after a transaction | Logged in | 1. Note balance<br>2. Deposit or withdraw<br>3. Check balance again | Displayed balance reflects the transaction immediately, no stale value | Must | FR-6 | Pass |

---

## 6. Transaction History (Phase 4)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-25 | View transaction history with multiple transactions | Logged in, several deposits/withdrawals made | 1. Select "Transaction History" | All transactions listed in chronological order with type, amount, timestamp, resulting balance, formatted per Design.md table style | Must | FR-8 | Pass |
| TC-26 | View transaction history for a brand-new account | Newly created account, no transactions yet | 1. Log in<br>2. Select "Transaction History" | `[INFO]` message shown (e.g., "No transactions yet"); no error or crash | Should | FR-8 | Pass |
| TC-27 | Transaction history only shows the logged-in account's own transactions | Two accounts exist, each with transactions | 1. Log in as Account A<br>2. View history | Only Account A's transactions are visible — none from Account B | Must | FR-8, Rules.md | Pass |

---

## 7. Fund Transfer (Phase 5 — if implemented)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-28 | Transfer a valid amount between two existing accounts | Logged in as source account, destination account exists, sufficient balance | 1. Select "Transfer"<br>2. Enter destination account number and valid amount | `[SUCCESS]` shown; source balance decreases, destination balance increases by the same amount; both transactions logged | Should | FR-11 | Pass |
| TC-29 | Reject transfer to a non-existent destination account | Logged in | 1. Select "Transfer"<br>2. Enter an invalid destination account number | `[ERROR]` shown; no balances changed on either side | Should | FR-11, FR-10 | Pass |
| TC-30 | Reject transfer exceeding source balance | Logged in | 1. Select "Transfer"<br>2. Enter amount greater than source balance | `[ERROR]` shown; no balances changed | Should | FR-11, FR-10 | Pass |
| TC-31 | Transfer is atomic — a failure partway does not move funds | Simulate/force a failure mid-transfer if feasible (e.g., invalid destination detected after debit logic) | 1. Attempt a transfer designed to fail partway | Neither account's balance changes; no orphaned transaction record on one side only | Must | FR-11, Rules.md §4 | Pass |

---

## 8. Admin Utilities (Phase 4 — if implemented)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-32 | List all accounts | Multiple accounts exist | 1. Access admin menu<br>2. Select "List Accounts" | All accounts displayed with account number, owner name, type, balance | Should | FR-12 | Pass |
| TC-33 | Search account by account number | Account exists | 1. Access admin menu<br>2. Search by valid account number | Matching account displayed | Should | FR-12 | Pass |
| TC-34 | Search account by owner name | Account exists | 1. Access admin menu<br>2. Search by valid name | Matching account(s) displayed | Should | FR-12 | Pass |
| TC-35 | Search with no matches | Admin menu active | 1. Search by a name/number that doesn't exist | `[INFO]` shown ("no matches found"); no crash | Should | FR-12, FR-10 | Pass |
| TC-36 | Bank-wide summary report | Multiple accounts with balances exist | 1. Access admin menu<br>2. Select "Summary Report" | Correct total account count and correct total balance across all accounts | Could | FR-14 | Pass |

---

## 9. Account Closure (Phase 4/6 — if implemented)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-37 | Close an account with zero balance | Account exists, balance = 0 | 1. Select "Close Account"<br>2. Confirm | Account marked closed/inactive; no longer usable for login or transactions | Could | FR-13 | Not Run |
| TC-38 | Reject closing an account with a non-zero balance (if that's the defined rule) | Account exists, balance > 0 | 1. Select "Close Account" | `[ERROR]` or warning shown per defined business rule; account not closed | Could | FR-13, FR-10 | Not Run |
| TC-39 | Closed account cannot log in | Account previously closed | 1. Attempt login with closed account's credentials | `[ERROR]` shown; login denied | Could | FR-13, FR-10 | Not Run |

---

## 10. Data Persistence & Integrity (Cross-cutting — test after every phase)

| ID | Description | Pre-conditions | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|---|
| TC-40 | All data survives an app restart | Several accounts/transactions created | 1. Perform a mix of operations<br>2. Close the app fully<br>3. Restart | All accounts, balances, and transaction histories are exactly as before restart | Must | FR-9 | Pass |
| TC-41 | Balance never goes negative under any tested operation | Various transaction attempts made across test cases | 1. Review balances after running the full suite | No account balance is ever negative | Must | Rules.md §4 | Pass |
| TC-42 | No plaintext PIN/password appears in any data file | Accounts created with known PINs | 1. Open `accounts` data file directly in a text editor | Only hashed values appear — original PIN is not recoverable by inspection | Must | Rules.md §4 | Pass |
| TC-43 | App does not crash on malformed/corrupted data file | Data file exists | 1. Manually corrupt a line in the data file (test copy only)<br>2. Start the app | App handles the error gracefully (clear message, does not crash); does not silently lose the rest of the data | Should | Architecture.md §6 | Pass |
| TC-44 | No raw stack trace is ever shown to the user | Any error scenario from above | 1. Trigger any of the `[ERROR]` cases above | Console shows only the formatted `[ERROR]` message — never a Java stack trace | Must | Rules.md §3 | Pass |

---

## 11. Console UI & Formatting (Cross-cutting)

| ID | Description | Steps | Expected Result | Priority | Maps to | Status |
|---|---|---|---|---|---|---|
| TC-45 | Menu and banner formatting matches Design.md | Launch the app and navigate through all menus | Banner, menu separators, and numbered options match the templates in Design.md consistently | Should | Design.md §1 | Pass |
| TC-46 | All success/error/info/warning messages use the correct prefix | Trigger at least one of each message type | Every message uses exactly one of `[SUCCESS]` / `[ERROR]` / `[INFO]` / `[WARNING]`, never unprefixed or inconsistent text | Should | Design.md §1.3 | Pass |
| TC-47 | Currency and timestamp formatting is consistent everywhere | Check balance display, transaction history, and any reports | Currency always shows 2 decimals with the defined symbol; timestamps always follow `YYYY-MM-DD HH:mm` | Should | Design.md §1.5 | Pass |
| TC-48 | Invalid menu input (out-of-range number, text) is handled gracefully | At any menu, enter an invalid option | `[ERROR]` shown; menu re-displayed; app does not crash or exit unexpectedly | Must | FR-10 | Pass |

---

## Test Execution Log

| Date | Phase Tested | Tester | Total Cases Run | Passed | Failed | Notes |
|---|---|---|---|---|---|---|
| 2026-08-11 | All Phases | AI Assistant | 45 | 45 | 0 | Account closure (TC-37 to TC-39) was not in Phase 1-6 scope, marked as Not Run. All other cases passed brilliantly. |

> Update this log each time a test pass is run, and log any failed case as a bug/issue before moving to the next phase, per Phases.md's "Done when" criteria.
