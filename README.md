# ✦ art & coin ✧

> make art. make money. keep receipts.

**art & coin** is a command-line accounting ledger for freelance artists. It tracks commissions (deposits) and supply costs (payments), saves everything to a CSV file, and lets you view, filter, report on and search your transactions.

## Features

- **Add deposit**: record money coming in (commissions, sales, invoices)
- **Make payment**: record money going out (supplies, fees, shipping)
- **Add another** right after saving, without going back to the menu
- **Ledger views**, all sorted from most recent
  - all transactions
  - deposits only
  - payments only
- **Reports**
  - month to date
  - previous month
  - year to date
  - previous year
  - search by vendor
  - custom search
- **Input validation**: bad numbers, dates and times are rejected and re-prompted
- **Persistent storage**: transactions are saved to `transactions.csv` and loaded on startup
- **Themed interface**: colored menus, box-drawing frames, symbols and a startup loading bar

## Requirements

- Java 17 or newer (the project uses text blocks)
- IntelliJ IDEA (or any Java IDE or the command line)
- A terminal with UTF-8 and ANSI color support (IntelliJ's Run window works)

## How to run

1. Clone or download the project.
2. Open it in IntelliJ IDEA.
3. Make sure `transactions.csv` is in the project root (the working directory).
4. Run `App.java`.

**Display troubleshooting**

- Symbols show as `?`: set **Settings → Editor → File Encodings** to UTF-8.
- Table borders look stretched or gappy: set the console font to JetBrains Mono with line spacing 1.0.
- Table lines wrap: widen the Run window. The transaction table is about 127 characters wide.

## Using the app

### Home screen

```
╔════════════════════════════════════════╗
║            ✦  art & coin  ✧            ║
║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
║  [1] ✦ add deposit                     ║
║  [2] ✧ make payment                    ║
║  [3] ⌑ go to ledger                    ║
║  [0] ✕ exit application                ║
╚════════════════════════════════════════╝
```

### Adding a transaction

Both **add deposit** and **make payment** ask for:

| Prompt | Format | Example |
|---|---|---|
| Date | `yyyy-mm-dd` | `2026-11-01` |
| Time | `hh:mm` or `hh:mm:ss` | `20:17:38` |
| Description | text | `Invoice 1015 paid - festival poster` |
| Vendor | text | `Riverside Jazz Fest` |
| Amount | number | `250.00` |

Enter the amount as a positive number. Deposits are saved as positive amounts and payments are saved as negative amounts automatically.

### Ledger

| Option | Shows |
|---|---|
| 1 | All transactions |
| 2 | Deposits only (positive amounts) |
| 3 | Payments only (negative amounts) |
| 4 | Reports menu |
| 0 | Back to home |

### Reports

| Option | Report |
|---|---|
| 1 | Month to date |
| 2 | Previous month |
| 3 | Year to date |
| 4 | Previous year |
| 5 | Search by vendor |
| 6 | Custom search |
| 0 | Back to ledger |

Date-range reports are listed from oldest to newest. Searches are listed from most recent.

**Search by vendor** matches part of a vendor name and ignores capitalization, so `etsy` finds `Etsy`.

**Custom search** asks for each filter in turn. Press Enter to skip any filter you don't want:

1. start and end date (the end date defaults to today if skipped)
2. description (partial match, not case sensitive)
3. vendor (partial match, not case sensitive)
4. lowest and highest amount (compared by absolute value, so it works for payments too)

Only transactions that match every filter you entered are shown.

## Data file format

Transactions are stored in `transactions.csv`, one per line, with `|` between fields. The first line is a header and is skipped when loading.

```
date|time|description|vendor|amount
2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00
```

New transactions are appended to the end of the file as you add them. Lines that can't be read are skipped with an error message.

## Project structure

| File | Purpose |
|---|---|
| `App.java` | Program entry point, screen flow, reports, searching, sorting, reading and writing the file |
| `UI.java` | Menus, headers, table header and color constants |
| `Console.java` | Input helpers that validate numbers, dates, times, date ranges and amount ranges |
| `Transaction.java` | Transaction model with formatted table row and CSV output |
| `LoadingBar.java` | Startup loading animation |
| `transactions.csv` | Saved transaction data |

## Concepts used

- Classes, objects, getters and setters
- `ArrayList` of custom objects
- Sorting with `Comparator`
- `BufferedReader` and `FileWriter` for file input and output
- `LocalDate`, `LocalTime` and `TemporalAdjusters`
- `try/catch` and input validation loops
- `switch` menus with `do-while` loops
- Formatted output with `printf`, `String.format` and text blocks
- ANSI color codes for terminal styling

## Known limitations

- Data is stored in a plain text file with no backup or encryption.
- Pipe characters (`|`) in a description or vendor would break that line when the file is loaded.
- Transactions can't be edited or deleted from inside the app.

## Ideas for future updates

- Running balance and totals for each report
- Edit and delete transactions
- Categories (supplies, commissions, shipping)
- Export reports to a file

## Author

Built by Elina as a capstone project.

## Acknowledgments

This README file was drafted by Claude Sonnet 5.5 (Anthropic) based on the project's source code, and reviewed by the author for accuracy and professional judgment.
