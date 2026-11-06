package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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
        UI.loadingBar(42, 50);
        File.loadTransactions();
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
        Transaction newTransaction = File.createTransaction(true);

        // add new object to transactions.csv
        File.appendTransaction(newTransaction);

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
        Transaction newTransaction = File.createTransaction(false);

        // add to transactions.csv
        File.appendTransaction(newTransaction);

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
        ListTransactions.sortFromMostRecent();

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
        ListTransactions.sortFromMostRecent();

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
        ListTransactions.sortFromMostRecent();

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
                case 1 -> ListTransactions.reportMonthToDate();
                case 2 -> ListTransactions.reportPreviousMonth();
                case 3 -> ListTransactions.reportYearToDate();
                case 4 -> ListTransactions.reportPreviousYear();
                case 5 -> ListTransactions.searchByVendor();
                case 6 -> ListTransactions.searchCustom();
                case 0 -> {
                }
                default -> System.out.println("\n△ Invalid command, please try again.");
            }
        } while (selectedOption != 0);
    }
}