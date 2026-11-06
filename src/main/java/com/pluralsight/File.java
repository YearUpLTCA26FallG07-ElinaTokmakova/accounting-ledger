package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class File {

    // this method loads transactions from file
    public static void loadTransactions() {

        try {
            String fileName = "transactions.csv";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));

            //skip first line in file
            bufferedReader.readLine();
            String line;

            //loop goes through every line in file
            while ((line = bufferedReader.readLine()) != null) {
                //convert each line to Transaction object
                Transaction t = getTransactionFromLine(line);

                if (!App.transactions.contains(t) && t != null) {
                    App.transactions.add(t);
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
    public static Transaction getTransactionFromLine(String line) {
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

    // this method adds new transaction to existing file
    public static void appendTransaction(Transaction t) {

        try {
            String fileName = "transactions.csv";
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(t.toEncodedString());
            writer.close();
        } catch (IOException e) {
            System.out.println("△ Something went wrong. Please try again.");
        }
    }

    //this method takes user input and creates Transaction object
    public static Transaction createTransaction(boolean isDeposit) {
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
        App.transactions.add(newTransaction);
        return newTransaction;
    }
}
