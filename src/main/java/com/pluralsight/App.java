package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;

public class App {

    public static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {

        loadTransactionsFromFile();
        LoadingBar.print(42, 12);
        displayHomeScreen();

    }

    // this method displays home screen
    private static void displayHomeScreen()
    {

        //todo add welcome message
        int selectedOption;
        do {
            clearConsole();
            selectedOption = Console.promptForInt("""
                    \n+----------------------------------------+
                    |                home page               |
                    +----------------------------------------+
                    |  [1] 💵 add deposit                    |
                    |  [2] 💳 make payment                   |
                    |  [3] 📑 go to ledger                   |
                    |  [0] 🚪 exit application               |
                    +----------------------------------------+
                    Select an option [0-3]:""");

            switch (selectedOption) {
                case 1 -> {
                    displayAddDeposit();
                }
                case 2 -> displayMakePayment();
                case 3 -> displayLedgerScreen();
                case 0 -> {
                }
                default -> System.out.println("\nInvalid command, please try again.");
            }
        } while (selectedOption != 0);

        System.out.println("Thanks for using our app. Bye!");
    }

    // this method processes "add deposit" input
    private static void displayAddDeposit() {
        //todo do you want to add transaction time manually or put it as "now"?
        clearConsole();
        System.out.println("""
                \n+----------------------------------------+
                |             💵 add deposit             |
                +----------------------------------------+""");

Transaction newTransaction = transactionInput();

        // add to transactions.csv
        appendTransactionToFile(newTransaction);

        int selectedOption;
        do {
            clearConsole();
            selectedOption = Console.promptForInt("""
                    \n+----------------------------------------+
                    |    💵 Deposit was successfully added.  |
                    +----------------------------------------+
                    |  [1] ➕ add another deposit            |
                    |  [2] ↩️ return to home screen          |
                    +----------------------------------------+
                    Select an option [1-2]:""");

            switch (selectedOption) {
                case 1 -> {
                    displayAddDeposit();
                    return;
                }
                case 2 -> {
                }
                default -> System.out.println("\nInvalid command, please try again.");
            }
        } while (selectedOption != 2);
    }

    // this method processes "make payment" input
    private static void displayMakePayment() {
//todo do you want to add transaction time manually or put it as "now"?

        clearConsole();
        System.out.println("""
                \n+----------------------------------------+
                |             💳 make payment            |
                +----------------------------------------+""");

        //convert date input to LocalDate
        LocalDate date = LocalDate.now();
        boolean isValid = false;

        do {
            try {
                String dateInput = Console.promptForString("\n\uD83D\uDDD3️ Date in format yyyy-mm-dd (e.g. 2026-11-01): ");
                date = LocalDate.parse(dateInput);
                isValid = true;     // Input was valid and conversion was successful, exit loop
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please double check format yyyy-mm-dd.");
            }
        } while (!isValid);

        //convert time input to LocalTime
        LocalTime time = LocalTime.now();
        isValid = false;

        do {
            try {
                String timeInput = Console.promptForString("\n⏳ Time in format hh:mm:ss (e.g. 20:17 or 20:17:38): ");
                time = LocalTime.parse(timeInput);
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please double check format hh:mm:ss.");
            }
        } while (!isValid);

        //description
        String description = Console.promptForString("\n\uD83D\uDCC3 Enter transaction description: ");

        //vendor
        String vendor = Console.promptForString("\n\uD83E\uDD11 Enter vendor name: ");

        //amount
        double amount = Console.promptForDouble("\n\uD83D\uDCB5 Enter amount: ");
        amount = -amount;

        // create Transaction object with user input
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);

        // add to transactions.csv1

        appendTransactionToFile(newTransaction);

        int selectedOption;
        do {
            clearConsole();
            selectedOption = Console.promptForInt("""
                    \n+----------------------------------------+
                    |   💳 Payment was successfully added.   |
                    +----------------------------------------+
                    |  [1] ➕ add another payment            |
                    |  [2] ↩️ return to home screen          |
                    +----------------------------------------+
                    Pick an option (1-2):\s""");

            switch (selectedOption) {
                case 1 -> {
                    displayMakePayment();
                    return;
                }
                case 2 -> {
                }
                default -> System.out.println("\nInvalid command, please try again.");
            }
        } while (selectedOption != 2);
    }

    // this method displays ledger screen
    private static void displayLedgerScreen() {

        int selectedOption;
        do {
            selectedOption = Console.promptForInt("""
                    \n+----------------------------------------+
                    |               📑 ledger                |
                    +----------------------------------------+
                    |  [1] 📋 view all transactions          |
                    |  [2] 💵 view deposits only             |
                    |  [3] 💳 view payments only             |
                    |  [4] 📊 view reports                   |
                    |  [0] ↩️ return to home screen          |
                    +----------------------------------------+
                    Select an option [0-4]:""");

            switch (selectedOption) {
                case 1 -> displayAllTransactions();
                case 2 -> displayDepositsOnly();
                case 3 -> displayPaymentsOnly();
                case 4 -> displayReports();
                case 0 -> {
                }
                default -> System.out.println("\nInvalid command, please try again.");
            }
        } while (selectedOption != 0);

    }

    // view all transactions screen
    private static void displayAllTransactions() {
        // table header
        System.out.println("""
                \n+-----------------------------------------------------------+
                |             all transactions (from most recent)           |
                +-----------------------------------------------------------+""");

        System.out.println("""
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+
                | date     | time     | description                                        | vendor                    |        amount in $ |
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+""");

        sortFromMostRecent();

        // print values
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // view deposits only screen
    private static void displayDepositsOnly() {
        // table header
        System.out.println("""
                \n+-----------------------------------------------------------+
                |             💵 all deposits (from most recent)            |
                +-----------------------------------------------------------+""");

        System.out.println("""
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+
                | date     | time     | description                                        | vendor                    |        amount in $ |
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+""");

        sortFromMostRecent();

        // print only positive values (debit)
        for (Transaction t : transactions) {
            if (t.getAmount() > 0) {
                System.out.println(t);
            }
        }
    }

    // view all payments screen
    private static void displayPaymentsOnly() {
        // table header
        System.out.println("""
                \n+-----------------------------------------------------------+
                |             💵 all payments (from most recent)            |
                +-----------------------------------------------------------+""");

        System.out.println("""
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+
                | date     | time     | description                                        | vendor                    |        amount in $ |
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+""");

        sortFromMostRecent();

        // print only positive values (debit)
        for (Transaction t : transactions) {
            if (t.getAmount() < 0) {
                System.out.println(t);
            }
        }
    }

    // reports screen - run pre-defined reports or a custom search
    private static void displayReports() {

        int selectedOption;
        do {
            //todo add option 6 - custom search
            // Start Date
            // End Date
            // Description
            // Vendor
            // Amount
            selectedOption = Console.promptForInt("""
                    \n+----------------------------------------+
                    |               📊 reports               |
                    +----------------------------------------+
                    |  [1] month to date                     |
                    |  [2] previous month                    |
                    |  [3] year to date                      |
                    |  [4] previous year                     |
                    |  [5] search by vendor                  |
                    |  [0] return to ledger                  |
                    +----------------------------------------+
                    Select an option [0-5]:""");

            switch (selectedOption) {
                case 1 -> reportMonthToDate();
                case 2 -> reportPreviousMonth();
                case 3 -> reportYearToDate();
                case 4 -> reportPreviousYear();
                case 5 -> searchByVendor();
                case 0 -> {}
                default -> System.out.println("\nInvalid command, please try again.");
            }
        } while (selectedOption != 0);
    }

    // this method shows report for month to date
    private static void reportMonthToDate() {

        // initialize first day of current month
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);

        // initialize today
        LocalDate endDate = LocalDate.now();

        showTransactionsInRange(startDate, endDate);
    }

    // this method shows report for previous month
    private static void reportPreviousMonth() {

        // initialize current month
        Month currentMonth = LocalDate.now().getMonth();
        int previousMonthValue = currentMonth.getValue() - 1;
        LocalDate startDate = LocalDate.now().withMonth(previousMonthValue).withDayOfMonth(1);

        // initialize last day of previous month
        LocalDate endDate = LocalDate.now().withMonth(previousMonthValue).with(TemporalAdjusters.lastDayOfMonth());

        showTransactionsInRange(startDate, endDate);
    }

    // this method shows report for year to date
    private static void reportYearToDate() {

        // initialize first day of current year
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());

        // initialize today
        LocalDate endDate = LocalDate.now();

        showTransactionsInRange(startDate, endDate);
    }

    // this method shows report for previous year
    private static void reportPreviousYear() {

        // initialize first day of previous year
        int currentYear = LocalDate.now().getYear();
        int previousYear = currentYear - 1;
        LocalDate startDate = LocalDate.now().withYear(previousYear).with(TemporalAdjusters.firstDayOfYear());

        // initialize last day of previous year
        LocalDate endDate = LocalDate.now().withYear(previousYear).with(TemporalAdjusters.lastDayOfYear());

        showTransactionsInRange(startDate, endDate);

    }

    // this method display transactions that are in range
    private static void showTransactionsInRange(LocalDate startDate, LocalDate endDate) {

        // table header
        System.out.printf("""
                \n+---------------------------------------------------------------------------------+
                |                         report (%s – %s)                        |
                +---------------------------------------------------------------------------------+""", startDate, endDate);
        System.out.println("""
                \n+----------+----------+----------------------------------------------------+---------------------------+--------------------+
                | date     | time     | description                                        | vendor                    |        amount in $ |
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+""");

        sortFromOldest();

        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate)) {
                System.out.println(t);
            }
        }
    }

    // this method looks for transactions by specific vendor
    private static void searchByVendor() {

        System.out.println("""
                \n+----------------------------------------+
                |      search transactions by vendor     |
                +----------------------------------------+""");

        // search prompt
        String prompt = Console.promptForString("Enter vendor name: ").toLowerCase();

        sortFromMostRecent();

        System.out.printf("\nSearch results for \"%s\":", prompt);

        //counter for printing header only once
        int i = 0;
        boolean isFound = false;
        for (Transaction t : transactions) {
            if (t.getVendor().toLowerCase().contains(prompt) && i == 0) {
                i++;
                System.out.println("""
                \n+----------+----------+----------------------------------------------------+---------------------------+--------------------+
                | date     | time     | description                                        | vendor                    |        amount in $ |
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+""");
                System.out.println(t);
                isFound = true;
            }
            else if (t.getVendor().toLowerCase().contains(prompt.toLowerCase())) {
                System.out.println(t);
            }
        }
        // if not found
        if (!isFound) {
        System.out.println("\nNo transactions found.");
        }
    }

    // this method loads transactions from file
    private static void loadTransactionsFromFile() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("transactions.csv"));

            //skip first line in file
            bufferedReader.readLine();
            String line;

            //loop goes through every line in file
            while ((line = bufferedReader.readLine()) != null) {
                //convert each line to Transaction object
                Transaction t = getTransactionFromLine(line);

                if (!transactions.contains(t) && t != null) {
                    transactions.add(t);
                }
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File was not found. Please try again.");

        } catch (IOException e) {
            System.out.println("Something went wrong. Please try again.");
        }
    }

    // this method converts each line from file to Transaction object
    private static Transaction getTransactionFromLine(String line) {

        //date|time|description|vendor|amount
        //2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
        //2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00

        // split each line into parts

        try {
            String[] parts = line.split(Pattern.quote("|"));

            LocalDate date = LocalDate.parse(parts[0]);
            LocalTime time = LocalTime.parse(parts[1]);
            String description = parts[2];
            String vendor = parts[3];
            double amount = Double.parseDouble(parts[4]);

            return new Transaction(date, time, description, vendor, amount);

        } catch (Exception e) {

            System.out.println("Something went wrong. Please try again.");
            return null;

        }
    }

    // this method sorts transaction starting from most recent
    private static void sortFromMostRecent() {
        transactions.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());
    }

    // this method sorts transaction starting from the oldest
    private static void sortFromOldest() {
        transactions.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime));
    }

    private static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private static void appendTransactionToFile(Transaction t) {

        try {
            FileWriter writer = new FileWriter("transactions.csv", true);
            writer.write(t.toEncodedString());
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Something went wrong. Please try again.");
        }
    }

    private static void transactionInput(boolean isDeposit) {
        // date input
        LocalDate date = Console.promptForDate("\uD83D\uDCC5 Date in format yyyy-mm-dd (e.g. 2026-11-01):");

        // time input
        LocalTime time = Console.promptForTime("\uD83D\uDD52 Time in format hh:mm:ss (e.g. 20:17 or 20:17:38):");

        // description input
        String description = Console.promptForString("\uD83D\uDCDD Enter transaction description: ");

        // vendor input
        String vendor = Console.promptForString("\uD83C\uDFF7️ Enter vendor name: ");

        // amount input
        double amount = Console.promptForDouble("\uD83D\uDCB5 Enter amount: ");

        if (!isDeposit) {
            amount = - amount;
        }

        // create Transaction object with user input
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);


    }
}