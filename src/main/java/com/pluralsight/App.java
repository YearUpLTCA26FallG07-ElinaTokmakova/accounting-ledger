package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Pattern;

public class App {

    public static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {

        //returns from  file
        //todo add loading
        loadTransactionsFromFile();
        displayHomeScreen();

    }

    // this method displays home screen
    private static void displayHomeScreen()
    {

        //todo add welcome message

        int selectedOption;
        do {
            selectedOption = Console.promptForInt("""
                    \n+----------------------------------------+
                    |               home screen              |
                    +----------------------------------------+
                    |  [1] 💵 add deposit                    |
                    |  [2] 💳 make payment                   |
                    |  [3] 📑 go to ledger                    |
                    |  [0] 🚪 exit application               |
                    +----------------------------------------+
                    Select an option [0-3]:""");

            switch (selectedOption) {
                case 1 -> displayAddDeposit();
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

        System.out.println("""
                \n+----------------------------------------+
                |             💵 add deposit             |
                +----------------------------------------+""");

        //convert date input to LocalDate
        LocalDate date = LocalDate.now();
        boolean isValid = false;

        do {
            try {
                String dateInput = Console.promptForString("\uD83D\uDCC5 Date in format yyyy-mm-dd (e.g. 2026-11-01): ");
                date = LocalDate.parse(dateInput);
                isValid = true;     // Input was valid and conversion was successful, exit loop
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please double check format yyyy-mm-dd.");
            }
        } while (!isValid);

        //TODO add try catch loop TIME PARSING!!1!!!!!111!!!1!
        //convert time input to LocalTime
        LocalTime time = LocalTime.now();
        isValid = false;

        do {
            try {
                String timeInput = Console.promptForString("\uD83D\uDD52 Time in format hh:mm:ss (e.g. 20:17 or 20:17:38): ");
                time = LocalTime.parse(timeInput);
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please double check format hh:mm:ss.");
            }
        } while (!isValid);

        //description
        String description = Console.promptForString("\uD83D\uDCDD Enter transaction description: ");

        //vendor
        String vendor = Console.promptForString("\uD83C\uDFF7️ Enter vendor name: ");

        //amount
        double amount = Console.promptForDouble("\uD83D\uDCB5 Enter amount: ");

        //create Transaction object with user input
        transactions.add(new Transaction(date, time, description, vendor, amount));

        int selectedOption;
        do {
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

        System.out.println("""
                \n+----------------------------------------+
                |              make payment              |
                +----------------------------------------+""");

        //convert date input to LocalDate
        LocalDate date = LocalDate.now();
        boolean isValid = false;

        do {
            try {
                String dateInput = Console.promptForString("\uD83D\uDDD3️ Date in format yyyy-mm-dd (e.g. 2026-11-01): ");
                date = LocalDate.parse(dateInput);
                isValid = true;     // Input was valid and conversion was successful, exit loop
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please double check format yyyy-mm-dd.");
            }
        } while (!isValid);

        //TODO add try catch loop TIME PARSING!!1!!!!!111!!!1!
        //convert time input to LocalTime
        LocalTime time = LocalTime.now();
        isValid = false;

        do {
            try {
                String timeInput = Console.promptForString("⏳ Time in format hh:mm:ss (e.g. 20:17 or 20:17:38): ");
                time = LocalTime.parse(timeInput);
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please double check format hh:mm:ss.");
            }
        } while (!isValid);

        //description
        String description = Console.promptForString("\uD83D\uDCC3 Enter transaction description: ");

        //vendor
        String vendor = Console.promptForString("\uD83E\uDD11 Enter vendor name: ");

        //amount
        double amount = Console.promptForDouble("\uD83D\uDCB5 Enter amount: ");
        amount = -amount;

        //create Transaction object with user input
        transactions.add(new Transaction(date, time, description, vendor, amount));

        int selectedOption;
        do {
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
                \n+---------------------------------------------------------+
                |             all transactions (from most recent)           |
                +-----------------------------------------------------------+""");

        System.out.println("""
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+
                | date     | time     | description                                        | vendor                    |        amount in $ |
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+""");

        sortFromMostRecent();

        // print values
        for (Transaction t : transactions) {
            System.out.println(t.toString());
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
                System.out.println(t.toString());
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
                System.out.println(t.toString());
            }
        }
    }

    // reports screen - run pre-defined reports or a custom search
    private static void displayReports() {

        int selectedOption;
        do {
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

        // initialize today
        LocalDate endDate = LocalDate.now();

        // initialize first day of current month
        LocalDate startDate = endDate.withDayOfMonth(1);

        showTransactionsInRange(startDate, endDate);
    }

    // this method shows report for previous month
    private static void reportPreviousMonth() {

        // initialize current month
        Month previousMonth = LocalDate.now().getMonth();
        int previousMonthValue = previousMonth.getValue() - 1;
        LocalDate startDate = LocalDate.now().withMonth(previousMonthValue).withDayOfMonth(1);

        // initialize last day of previous month
        LocalDate endDate = LocalDate.now().withMonth(previousMonthValue).with(TemporalAdjusters.lastDayOfMonth());

        showTransactionsInRange(startDate, endDate);
    }

    // this method shows report for year to date
    private static void reportYearToDate() {

    }

    // this method shows report for previous year
    private static void reportPreviousYear() {
    }

    // this method display transactions that are in range
    private static void showTransactionsInRange(LocalDate startDate, LocalDate endDate) {

        // table header
        System.out.printf("""
                \n\n+---------------------------------------------------------------------------------+
                |                report for month to date (%s – %s)               |
                +---------------------------------------------------------------------------------+\n""", startDate, endDate);
        System.out.println("""
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+
                | date     | time     | description                                        | vendor                    |        amount in $ |
                +----------+----------+----------------------------------------------------+---------------------------+--------------------+""");

        sortFromOldest();

        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate)) {
                System.out.println(t.toString());
            }
        }
    }

    // this method shows report for specific vendor
    private static void searchByVendor() {
    }

    // this method loads transactions from file
    private static void loadTransactionsFromFile() {
        // todo create new arraylist
        //  add try/catch
        //  file reader + buffered reader
        //  loop each non empty line to create Transaction object

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
}