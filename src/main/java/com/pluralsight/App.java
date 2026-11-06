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

    public static void main(String[] args) throws InterruptedException {

        System.out.println("""
                ╔════════════════════════════════════════╗
                ║            ✦  art & coin  ✦            ║
                ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                ║          make art. make money.         ║
                ║             keep receipts.             ║
                ╚════════════════════════════════════════╝
                """);
        LoadingBar.print(42, 50);
        loadTransactionsFromFile();
        displayHomeScreen();

    }

    // this method displays home screen
    private static void displayHomeScreen() {
        int selectedOption;

        do {
            selectedOption = UI.menuHome();
            switch (selectedOption) {
                case 1 -> displayAddDeposit();
                case 2 -> displayMakePayment();
                case 3 -> displayLedgerScreen();
                case 0 -> {
                }
                default -> System.out.println("\n△ Invalid command, please try again.");
            }
        } while (selectedOption != 0);

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n✦  thanks for using art & coin  ✧");
    }

    // this method processes "add deposit" input
    private static void displayAddDeposit() {
        UI.addDepositHeader();

        // input prompts and create new object
        Transaction newTransaction = createTransactionFromFile(true);

        // add new object to transactions.csv
        appendTransactionToFile(newTransaction);

        int selectedOption;
        do {
            selectedOption = UI.addAnotherDeposit();
            switch (selectedOption) {
                case 1 -> {
                    displayAddDeposit();
                    return;
                }
                case 2 -> {
                }
                default -> System.out.println("\n△ Invalid command, please try again.");
            }
        } while (selectedOption != 2);
    }

    // this method processes "make payment" input
    private static void displayMakePayment() {
//todo do you want to add transaction time manually or put it as "now"?
        UI.makePayment();
        // input prompts and create new object
        Transaction newTransaction = createTransactionFromFile(false);

        // add to transactions.csv1
        appendTransactionToFile(newTransaction);

        int selectedOption;
        do {
            selectedOption = UI.addAnotherPayment();
            switch (selectedOption) {
                case 1 -> {
                    displayMakePayment();
                    return;
                }
                case 2 -> {
                }
                default -> System.out.println("\n△ Invalid command, please try again.");
            }
        } while (selectedOption != 2);
    }

    // this method displays ledger screen
    private static void displayLedgerScreen() {
        int selectedOption;
        do {
            selectedOption = UI.menuLedger();
            switch (selectedOption) {
                case 1 -> displayAllTransactions();
                case 2 -> displayDepositsOnly();
                case 3 -> displayPaymentsOnly();
                case 4 -> displayReports();
                case 0 -> {
                }
                default -> System.out.println("\n△ Invalid command, please try again.");
            }
        } while (selectedOption != 0);
    }

    // view all transactions screen
    private static void displayAllTransactions() {
        // table header
        System.out.println(UI.BLUE + """
                \n✦  all transactions (from most recent)  ✧""" + UI.RESET);
        UI.tableHeader();
        sortFromMostRecent();

        // print values
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // view deposits only screen
    private static void displayDepositsOnly() {
        // table header
        System.out.println(UI.BLUE + """
                \n✦  all deposits (from most recent)  ✦""" + UI.RESET);
        UI.tableHeader();
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
        System.out.println(UI.BLUE + """
                \n✧  all payments (from most recent)  ✧""" + UI.RESET);
        UI.tableHeader();
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
            selectedOption = UI.menuReports();
            switch (selectedOption) {
                case 1 -> reportMonthToDate();
                case 2 -> reportPreviousMonth();
                case 3 -> reportYearToDate();
                case 4 -> reportPreviousYear();
                case 5 -> searchByVendor();
                case 6 -> searchCustom();
                case 0 -> {
                }
                default -> System.out.println("\n△ Invalid command, please try again.");
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
        // initialize first day of previous month safely (handles January -> previous December automatically)
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);

        // initialize last day of previous month
        LocalDate endDate = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

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

    // this method displays transactions that are in specific range
    private static void showTransactionsInRange(LocalDate startDate, LocalDate endDate) {
        // table header
        System.out.printf("""
                \n◫  report for %s – %s  (from oldest)  ◫%n""", startDate, endDate);
        UI.tableHeader();
        sortFromOldest();

        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate)) {
                System.out.println(t);
            }
        }
    }

    // this method looks for transactions by specific vendor
    private static void searchByVendor() {
        UI.searchByVendor();
        // search prompt
        String prompt = Console.promptForString("Enter vendor name: ").toLowerCase();
        sortFromMostRecent();
        //output
        System.out.printf(UI.BLUE + "\n✧ search results for \"%s\" (from most recent) ✧%n" + UI.RESET, prompt);

        //counter for printing header only once
        int i = 0;
        boolean isFound = false;
        for (Transaction t : transactions) {
            if (t.getVendor().toLowerCase().contains(prompt) && i == 0) {
                i++;
                UI.tableHeader();
                System.out.println(t);
                isFound = true;
            } else if (t.getVendor().toLowerCase().contains(prompt.toLowerCase())) {
                System.out.println(t);
            }
        }
        // if not found
        if (!isFound) {
            System.out.println("\nNo transactions found.");
        }
    }

    // this method allows to search by each transaction value
    private static void searchCustom() {
        UI.customSearch();

        //run search for date range
        LocalDate[] dateRange = Console.promptForDateRange();
        LocalDate startDate = dateRange[0];
        LocalDate endDate = dateRange[1];

        ArrayList<Transaction> dateRangeResults = new ArrayList<>();
        for (Transaction t : transactions) {
            if (startDate == null || (!t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate))) {
                dateRangeResults.add(t);
            }
        }

        // run search for description
        String description = Console.promptForString("Enter description (press Enter↵ to skip): ").toLowerCase();

        ArrayList<Transaction> descriptionResults = new ArrayList<>();
        for (Transaction t : dateRangeResults) {
            if (description.isEmpty() || t.getDescription().toLowerCase().contains(description)) {
                descriptionResults.add(t);
            }
        }

        // run search for vendor
        String vendor = Console.promptForString("Enter vendor (press Enter↵ to skip): ").toLowerCase();

        ArrayList<Transaction> vendorResults = new ArrayList<>();
        for (Transaction t : descriptionResults) {
            if (vendor.isEmpty() || t.getVendor().toLowerCase().contains(vendor)) {
                vendorResults.add(t);
            }
        }

        // run search for amount range
        Double[] amountRange = Console.promptForAmountRange();
        Double lowestAmount = amountRange[0];
        Double highestAmount = amountRange[1];

        ArrayList<Transaction> amountRangeResults = new ArrayList<>();
        for (Transaction t : vendorResults) {
            if ((lowestAmount == null || Math.abs(t.getAmount()) >= lowestAmount) && (highestAmount == null || Math.abs(t.getAmount()) <= highestAmount)) {
                amountRangeResults.add(t);
            }
        }

        // initialize values for format print
        // date text
        String dateText;
        if (startDate == null) {
            dateText = "any date";
        } else {
            dateText = startDate + " to " + endDate;
        }

        // description text
        String descText;
        if (description.isEmpty()) {
            descText = "any";
        } else {
            descText = description;
        }

        // vendor text
        String vendorText;
        if (vendor.isEmpty()) {
            vendorText = "any";
        } else {
            vendorText = vendor;
        }

        // amount text
        String amountText;
        if (lowestAmount == null && highestAmount == null) {
            amountText = "any";
        } else if (lowestAmount != null && highestAmount != null) {
            amountText = "$" + lowestAmount + " - $" + highestAmount;
        } else if (lowestAmount != null) {
            amountText = ">= $" + lowestAmount;
        } else {
            amountText = "<= $" + highestAmount;
        }

        // output
        sortFromMostRecent();
        System.out.printf(UI.BLUE + "\n✧ search results ✧" +
                "%n(date: %s ║ description: %s ║ vendor: %s ║ amount: %s)%n" + UI.RESET, dateText, descText, vendorText, amountText);
        UI.tableHeader();

        for (Transaction t : amountRangeResults) {
            System.out.println(t);
        }

        if (amountRangeResults.isEmpty()) {
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
            System.out.println("△ Something went wrong. Please try again.");
        }
    }

    // this method converts each line from file to Transaction object
    private static Transaction getTransactionFromLine(String line) {
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
            System.out.println(UI.RED + "△ Something went wrong. Please try again." + UI.RESET);
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

    // this method adds new transaction to existing file
    private static void appendTransactionToFile(Transaction t) {

        try {
            FileWriter writer = new FileWriter("transactions.csv", true);
            writer.write(t.toEncodedString());
            writer.close();
        } catch (IOException e) {
            System.out.println("△ Something went wrong. Please try again.");
        }
    }

    //this method takes user input and creates Transaction object
    private static Transaction createTransactionFromFile(boolean isDeposit) {
        // date input
        LocalDate date = Console.promptForDate("▦ Date in format yyyy-mm-dd (e.g. 2026-11-01): ");

        // time input
        LocalTime time = Console.promptForTime("◷ Time in format hh:mm:ss (e.g. 20:17 or 20:17:38): ");

        // description input
        String description = Console.promptForString("✎ Enter transaction description: ");

        // vendor input
        String vendor = Console.promptForString("❖️ Enter vendor name: ");

        // amount input
        double amount = Console.promptForDouble("$ Enter amount: ");
        if (!isDeposit) {
            amount = -amount;
        }

        // create Transaction object with user input
        Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
        transactions.add(newTransaction);
        return newTransaction;
    }
}