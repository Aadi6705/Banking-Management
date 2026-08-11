# Design.md — Visual & Interaction Design

The v1 product is a **console (CLI) application**, so "design" here means consistent formatting, structure, and readability of terminal output — plus a forward-looking spec for the optional Phase 7 GUI, so future work stays visually consistent.

---

## 1. Console UI Design (v1 — applies now)

### 1.1 Layout Principles
- Every screen/menu starts with a clear header banner and ends with a clear separator, so output doesn't blur together in a scrolling terminal.
- Use consistent box-drawing/separator characters throughout — don't mix styles.
- Menus are numbered lists; the user types a number, never free text, to navigate.
- Error and success messages are visually distinct via prefixes (see §1.3), since color may not render in all terminals.

### 1.2 Standard Templates

**App banner (shown once at startup):**
```
==================================================
        BANKING MANAGEMENT SYSTEM (BMS)
==================================================
```

**Menu template:**
```
--------------------------------------------------
 MAIN MENU
--------------------------------------------------
 1. Create Account
 2. Login
 3. Exit
--------------------------------------------------
 Select an option:
```

**Success message:**
```
[SUCCESS] Deposit of ₹5,000.00 completed. New balance: ₹15,000.00
```

**Error message:**
```
[ERROR] Withdrawal denied — insufficient balance.
```

**Table (transaction history):**
```
--------------------------------------------------
 TYPE        AMOUNT        DATE/TIME            BALANCE
--------------------------------------------------
 DEPOSIT     ₹5,000.00     2026-08-11 14:32     ₹15,000.00
 WITHDRAW    ₹2,000.00     2026-08-10 09:10     ₹10,000.00
--------------------------------------------------
```

### 1.3 Message Prefixes (used consistently everywhere)
| Prefix | Meaning |
|---|---|
| `[SUCCESS]` | Operation completed |
| `[ERROR]` | Operation failed / invalid input |
| `[INFO]` | Neutral information (e.g., "No transactions yet") |
| `[WARNING]` | Non-blocking caution (e.g., "Balance is below recommended minimum") |

### 1.4 Optional ANSI Color (if terminal supports it)
If color is added (nice-to-have, not required), keep it minimal and semantic:
- Green → `[SUCCESS]`
- Red → `[ERROR]`
- Yellow → `[WARNING]`
- Cyan/blue → headers and menu titles
- Default/white → body text and data

Do not rely on color alone to convey meaning — always pair with the text prefix (§1.3), for accessibility and for terminals without ANSI support.

### 1.5 Currency & Formatting Conventions
- Currency symbol: **₹ (INR)** by default — configurable if needed.
- Always show 2 decimal places for money: `₹1,250.00`, not `₹1250` or `₹1250.0`.
- Timestamps: `YYYY-MM-DD HH:mm` (24-hour clock), consistent everywhere.
- Account numbers: fixed-length, zero-padded (e.g., `ACC-000123`) for visual alignment in tables.

---

## 2. Typography & Tone (text content)
- Menu/option text: short, imperative, sentence case ("Create account", not "CREATE ACCOUNT" or "Creating an Account").
- Error messages: state what went wrong and why, in plain language — no raw exception text or stack traces (see Rules.md §3).
- Avoid jargon in user-facing text; reserve technical terms for code comments/logs only.

---

## 3. Future GUI Design Spec (Phase 7 — Swing/JavaFX, if pursued)

If a GUI is built later, carry these tokens forward for visual consistency with the CLI's intent (trustworthy, clean, financial-app feel):

### 3.1 Color Palette
| Role | Color | Hex |
|---|---|---|
| Primary (brand/header) | Deep Blue | `#0B3D66` |
| Secondary (accents) | Teal | `#1B998B` |
| Success | Green | `#2E7D32` |
| Error | Red | `#C62828` |
| Warning | Amber | `#F9A825` |
| Background | Off-white | `#F5F7FA` |
| Text (primary) | Charcoal | `#212529` |
| Text (secondary/muted) | Slate Gray | `#6C757D` |

### 3.2 Typography
- **Headings:** A clean sans-serif (e.g., "Segoe UI", "Inter", or platform default bold) for menu titles/section headers.
- **Body/data:** A monospaced font (e.g., "Consolas", "JetBrains Mono") for tables and financial figures, so numbers align visually.
- **Sizing:** Headings 18–22px, body 14px, table data 13–14px monospace.

### 3.3 Layout Principles (GUI)
- Left-hand navigation (Accounts, Transactions, Reports) with a main content panel — familiar banking-dashboard pattern.
- Financial figures right-aligned in tables; text left-aligned.
- Primary actions (Deposit, Withdraw, Transfer) as prominent buttons in Primary Blue; destructive actions (Close Account) in Error Red with a confirmation dialog.
- Consistent 8px spacing grid for padding/margins.

This section is a forward-looking reference only — nothing here should be implemented until Phase 7 is explicitly reached (Rules.md §6–7).
