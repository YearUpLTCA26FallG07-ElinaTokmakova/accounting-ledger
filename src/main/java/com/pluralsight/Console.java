package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {
    private static final Scanner scanner = new Scanner(System.in);

    public static String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int promptForInt(String prompt) {
        int result = 0;
        boolean isValid = false;

        do {
            try {
                System.out.print(prompt);
                result = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                isValid = true;     // Input was successful, exit loop
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid whole number.\n");
                scanner.nextLine(); // Clear the bad input from the scanner buffer
            } catch (Exception e) {
                System.out.println("Something went wrong. Please try again.\n");
                scanner.nextLine(); // Clear the bad input from the scanner buffer
            }
        } while (!isValid);

        return result;
    }

    public static double promptForDouble(String prompt) {
        double input = 0.00;
        boolean isValid = false;

        do {
            try {
                System.out.print(prompt);
                input = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline
                isValid = true;     // Input was successful, exit loop
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.\n");
                scanner.nextLine(); // Clear the bad input from the scanner buffer
            } catch (Exception e) {
                System.out.println("Something went wrong. Please try again.\n");
                scanner.nextLine(); // Clear the bad input from the scanner buffer
            }
        } while (!isValid);

        return input;
    }

    public static LocalDate promptForDate(String prompt) {
        LocalDate date = null;
        boolean isValid = false;

        do {
            try {
                System.out.print(prompt);
                String dateInput = scanner.nextLine().trim();
                date = LocalDate.parse(dateInput);
                isValid = true;     // Input was valid and conversion was successful, exit loop

            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please double check format yyyy-mm-dd.\n");

            } catch (Exception e) {
                System.out.println("Something went wrong. Please try again.\n");
            }

        } while (!isValid);

        return date;
    }

    public static LocalTime promptForTime(String prompt) {
        LocalTime time = null;
        boolean isValid = false;

        do {
            try {
                System.out.print(prompt);
                String timeInput = scanner.nextLine().trim();
                time = LocalTime.parse(timeInput);
                isValid = true;     // Input was valid and conversion was successful, exit loop

            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please double check format hh:mm:ss.\n");

            } catch (Exception e) {
                System.out.println("Something went wrong. Please try again.\n");
            }
        } while (!isValid);

        return time;
    }
}