package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

//        loadTransactionsFromFile();
        showHomeScreen();

    }

    private static void showHomeScreen() {

        /**
         *         home screen:
         *         // option 1 - add deposit
         *         // option 2 - make payment (debit)
         *         // option 3 - go to the ledger screen
         *         // option 4 - exit the application
         */

        int selectedOption = 0;
        do { //as loop
            System.out.println("""
           \n+----------------------------------------+
           |               home screen              |
           +----------------------------------------+
           |  [1] add deposit                       |
           |  [2] make payment (debit)              |
           |  [3] go to ledger                      |
           |  [0] exit application                  |
           +----------------------------------------+""");

            selectedOption = Console.promptForInt("Pick an option (0-3): ");

            switch (selectedOption) {
                case 1 -> displayAddDeposit();
                case 2 -> displayMakePayment();
                case 3 -> displayLedgerScreen();
                case 0 -> {}
                default -> System.out.println("\nInvalid command, please try again.");
            }
        } while (selectedOption != 0);

        System.out.println("Thanks for using our app. Goodbye!");
    }

    private static ArrayList<Transaction> displayAddDeposit() {
        //todo do you want to add transaction time manually or put it as "now"?

        System.out.println("""
           \n+----------------------------------------+
           |               add deposit              |
           +----------------------------------------+""");

        //convert date input to LocalDate
        String dateInput = Console.promptForString("Enter a date in format yyyy-mm-dd (e.g. 2026-11-01): ");
        LocalDate date = LocalDate.parse(dateInput);

        //convert time input to LocalTime
        String timeInput = Console.promptForString("Enter a date in format hh:mm:ss (e.g. 20:17:38): ");
        LocalTime time = LocalTime.parse(timeInput);

        //description
        String description = Console.promptForString("Enter transaction description:");

        //vendor
        String vendor = Console.promptForString("Enter vendor name:");

        //amount
        double amount = Console.promptForDouble("Enter amount:");

        //create Transaction object
        try {
            transactions.add = new Transaction(date, time, description, vendor, amount);
        }
    }


    private static void displayMakePayment() {
        /**
         *        prompt for:
         *        date
         *        time
         *        description
         *        vendor
         *        amount (negative numbers)
         */

    }

    private static void displayLedgerScreen() {
        /**
         *         // option 1 - show all transactions
         *         // option 2 - show deposits only
         *         // option 3 - show payments only
         *         // option 4 - go to reports
         *         // option 5 - go back to home screen
         */

    }



    //this method will load from file
    private static ArrayList<Transaction> loadTransactionsFromFile() {
        // todo create new arraylist

        ArrayList<Transaction> transactions = loadTransactionsFromFile();
        // todo file reader + buffered reader

        // todo loop each non empty line to create Transaction object
        /** get transaction from data line method */

        // todo add try/catch

        // return arraylist
        return transactions;
    }

    // todo will use this method in loadTransactionFromFile()
//    private static Transaction getTransactionFromDataLine(String line) {
//        //date|time|description|vendor|amount
//        //2023-04-15|10:13:25|ergonomic keyboard|Amazon|-89.50
//        //2023-04-15|11:15:00|Invoice 1001 paid|Joe|1500.00
//
//        //todo split each into parts
//        //todo add try/catch
//        // return Transaction
//        return;
//    }
}