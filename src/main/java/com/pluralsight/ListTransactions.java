package com.pluralsight;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;

public class ListTransactions {
    // this method shows report for month to date
    public static void reportMonthToDate() {
        // initialize first day of current month
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);

        // initialize today
        LocalDate endDate = LocalDate.now();

        showTransactionsInRange(startDate, endDate);
    }

    // this method shows report for previous month
    public static void reportPreviousMonth() {
        // initialize first day of previous month safely (handles January -> previous December automatically)
        LocalDate startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1);

        // initialize last day of previous month
        LocalDate endDate = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        showTransactionsInRange(startDate, endDate);
    }

    // this method shows report for year to date
    public static void reportYearToDate() {
        // initialize first day of current year
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());

        // initialize today
        LocalDate endDate = LocalDate.now();

        showTransactionsInRange(startDate, endDate);
    }

    // this method shows report for previous year
    public static void reportPreviousYear() {
        // initialize first day of previous year
        int currentYear = LocalDate.now().getYear();
        int previousYear = currentYear - 1;
        LocalDate startDate = LocalDate.now().withYear(previousYear).with(TemporalAdjusters.firstDayOfYear());

        // initialize last day of previous year
        LocalDate endDate = LocalDate.now().withYear(previousYear).with(TemporalAdjusters.lastDayOfYear());

        showTransactionsInRange(startDate, endDate);

    }

    // this method looks for transactions by specific vendor
    public static void searchByVendor() {
        UI.searchByVendor();
        // search prompt
        String prompt = Console.promptForString("Enter vendor name: ").toLowerCase();
        ListTransactions.sortFromMostRecent();
        //output
        System.out.printf(UI.BLUE + "\n✧ search results for \"%s\" (from most recent) ✧%n" + UI.RESET, prompt);

        //counter for printing header only once
        int i = 0;
        boolean isFound = false;
        for (Transaction t : App.transactions) {
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
    public static void searchCustom() {
        UI.customSearch();

        //run search for date range
        LocalDate[] dateRange = Console.promptForDateRange();
        LocalDate startDate = dateRange[0];
        LocalDate endDate = dateRange[1];

        ArrayList<Transaction> dateRangeResults = new ArrayList<>();
        for (Transaction t : App.transactions) {
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
        ListTransactions.sortFromMostRecent();
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

    // this method displays transactions that are in specific range
    public static void showTransactionsInRange(LocalDate startDate, LocalDate endDate) {
        // table header
        System.out.printf(UI.BLUE + """
                \n◫  report for %s – %s  (from oldest)  ◫%n""" + UI.RESET, startDate, endDate);
        UI.tableHeader();
        sortFromOldest();

        for (Transaction t : App.transactions) {
            if (!t.getDate().isBefore(startDate) && !t.getDate().isAfter(endDate)) {
                System.out.println(t);
            }
        }
    }

    // this method sorts transaction starting from most recent
    public static void sortFromMostRecent() {
        App.transactions.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());
    }

    // this method sorts transaction starting from the oldest
    public static void sortFromOldest() {
        App.transactions.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime));
    }
}
