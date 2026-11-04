package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class App {

    public static ArrayList<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {

        //returns from  file
        loadTransactionsFromFile();
        displayHomeScreen();

    }
    // display home screen
    public static void displayHomeScreen() {

        int selectedOption;
        do {
            selectedOption = Console.promptForInt("""
                    \n+----------------------------------------+
                    |               home screen              |
                    +----------------------------------------+
                    |  [1] add deposit (credit)              |
                    |  [2] make payment (debit)              |
                    |  [3] go to ledger                      |
                    |  [0] exit application                  |
                    +----------------------------------------+
                    Pick an option (0-3):\s""");

            switch (selectedOption) {
                case 1 -> displayAddDeposit();
                case 2 -> displayMakePayment();
                case 3 -> displayLedgerScreen();
                case 0 -> {}
                default -> System.out.println("\nInvalid command, please try again.");
            }
        } while (selectedOption != 0);

        System.out.println("Thanks for using our app. Bye!");
    }
    //add deposit
    public static void displayAddDeposit() {
        //todo do you want to add transaction time manually or put it as "now"?

        System.out.println("""
                \n+----------------------------------------+
                |               add deposit              |
                +----------------------------------------+""");

        //convert date input to LocalDate
        LocalDate date = LocalDate.now();
        boolean isValid = false;

        do {
            try {
                String dateInput = Console.promptForString("\uD83D\uDDD3️ Date in format yyyy-mm-dd (e.g. 2026-11-01): ");
                date = LocalDate.parse(dateInput);
                isValid = true;     // Input was valid and conversion was successful, exit loop
            }
            catch (DateTimeParseException e){
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
            }
            catch (DateTimeParseException e){
                System.out.println("Invalid input. Please double check format hh:mm:ss.");
            }
        } while(!isValid);

        //description
        String description = Console.promptForString("\uD83D\uDCC3 Enter transaction description: ");

        //vendor
        String vendor = Console.promptForString("\uD83E\uDD11 Enter vendor name: ");

        //amount
        double amount = Console.promptForDouble("\uD83D\uDCB5 Enter amount: ");

        //create Transaction object with user input
        transactions.add(new Transaction(date, time, description, vendor, amount));

        int selectedOption;
        do {
            selectedOption = Console.promptForInt("""
                    \n+----------------------------------------+
                    |     Deposit was successfully added.    |
                    +----------------------------------------+
                    |  [1] add another deposit               |
                    |  [2] back to home screen               |
                    +----------------------------------------+
                    Pick an option (1-2):\s""");

            switch (selectedOption) {
                case 1 -> {
                    displayAddDeposit();
                    return;
                }
                case 2 -> {}
                default -> System.out.println("\nInvalid command, please try again.");
            }
        } while (selectedOption != 2);
    }

    public static void displayMakePayment() {
//todo do you want to add transaction time manually or put it as "now"?

        System.out.println("""
                \n+----------------------------------------+
                |               add deposit              |
                +----------------------------------------+""");

        //convert date input to LocalDate
        LocalDate date = LocalDate.now();
        boolean isValid = false;

        do {
            try {
                String dateInput = Console.promptForString("\uD83D\uDDD3️ Date in format yyyy-mm-dd (e.g. 2026-11-01): ");
                date = LocalDate.parse(dateInput);
                isValid = true;     // Input was valid and conversion was successful, exit loop
            }
            catch (DateTimeParseException e){
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
            }
            catch (DateTimeParseException e){
                System.out.println("Invalid input. Please double check format hh:mm:ss.");
            }
        } while(!isValid);

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
                    |     Payment was successfully added.    |
                    +----------------------------------------+
                    |  [1] add another payment               |
                    |  [2] back to home screen               |
                    +----------------------------------------+
                    Pick an option (1-2):\s""");

            switch (selectedOption) {
                case 1 -> {
                    displayMakePayment();
                    return;
                }
                case 2 -> {}
                default -> System.out.println("\nInvalid command, please try again.");
            }
        } while (selectedOption != 2);
    }

    public static void displayLedgerScreen() {
        /*
                  // option 1 - display all transactions
                  // option 2 - display deposits only
                  // option 3 - display payments only
                  // option 4 - go to reports
                  // option 5 - go back to home screen
         */

    }

    //this method loads transactions from file
    public static void loadTransactionsFromFile() {
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
            while ((line = bufferedReader.readLine())!= null) {
                //convert each line to Transaction object
                Transaction t = getTransactionFromLine(line);

                if (!transactions.contains(t) &&  t != null) {
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
    public static Transaction getTransactionFromLine(String line) {

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
}