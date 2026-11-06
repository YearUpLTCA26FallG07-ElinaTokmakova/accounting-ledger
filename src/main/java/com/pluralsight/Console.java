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
                System.out.println(UI.RED + "△ Invalid input. Please enter a valid whole number.\n" + UI.RESET);
                scanner.nextLine(); // Clear the bad input from the scanner buffer
            } catch (Exception e) {
                System.out.println(UI.RED + "△ Something went wrong. Please try again.\n" + UI.RESET);
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
                System.out.println(UI.RED + "△ Invalid input. Please enter a valid number.\n" + UI.RESET);
                scanner.nextLine(); // Clear the bad input from the scanner buffer
            } catch (Exception e) {
                System.out.println(UI.RED + "△ Something went wrong. Please try again.\n" + UI.RESET);
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
                System.out.println(UI.RED + "△ Invalid input. Please double check format yyyy-mm-dd.\n" + UI.RESET);

            } catch (Exception e) {
                System.out.println(UI.RED + "△ Something went wrong. Please try again.\n" + UI.RESET);
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
                System.out.println(UI.RED + "△ Invalid input. Please double check format hh:mm:ss.\n" + UI.RESET);

            } catch (Exception e) {
                System.out.println(UI.RED + "△ Something went wrong. Please try again.\n" + UI.RESET);
            }
        } while (!isValid);

        return time;
    }

    public static LocalDate[] promptForDateRange() {
        LocalDate startDate = null;
        boolean isValid = false;

        do {
            // start date of range
            String startDateString = promptForString("Enter start date for range (yyyy-mm-dd or press Enter↵ to skip)): ").trim();

            if (startDateString.isEmpty()) {
                isValid = true; // skip
            } else {
                try {
                    startDate = LocalDate.parse(startDateString);
                    isValid = true;     // input was valid and conversion was successful, exit loop
                } catch (DateTimeParseException e) {
                    System.out.println(UI.RED + "△ Invalid input. Please double check format yyyy-mm-dd.\n" + UI.RESET);

                } catch (Exception e) {
                    System.out.println(UI.RED + "△ Something went wrong. Please try again.\n" + UI.RESET);
                }
            }

        } while (!isValid);

        // prompt for end date of range only if user entered start date
        LocalDate endDate = null;

        if (startDate != null) {
            isValid = false;
            do {
                String endDateString = Console.promptForString("Enter end date for range (yyyy-mm-dd or press Enter↵ to skip): ");
                if (endDateString.isEmpty()) {
                    endDate = LocalDate.now(); // if end date was skipped it defaults to today
                    isValid = true; // skip
                } else {
                    try {
                        endDate = LocalDate.parse(endDateString);
                        isValid = true; // input was valid and conversion was successful, exit loop
                    } catch (DateTimeParseException e) {
                        System.out.println(UI.RED + "△ Invalid input. Please double check format yyyy-mm-dd.\n" + UI.RESET);

                    } catch (Exception e) {
                        System.out.println("△ Something went wrong. Please try again.\n");
                    }
                }
            } while (!isValid);
        }

        return new LocalDate[]{startDate, endDate};
    }

    public static Double[] promptForAmountRange() {
        Double lowestAmount = null;
        boolean isValid = false;

        do {
            // lowest amount of range
            String lowestString = promptForString("Enter lowest amount for range (or press Enter↵ to skip): ").trim();

            if (lowestString.isEmpty()) {
                isValid = true; // skip
            } else {
                try {
                    lowestAmount = Double.parseDouble(lowestString);
                    isValid = true;     // input was valid and conversion was successful, exit loop
                } catch (NumberFormatException e) {
                    System.out.println(UI.RED + "△ Invalid input. Please enter a valid number.\n" + UI.RESET);
                } catch (Exception e) {
                    System.out.println(UI.RED + "△ Something went wrong. Please try again.\n" + UI.RESET);
                }
            }
        } while (!isValid);

        // prompt for highest amount of range only if user entered lowest amount
        Double highestAmount = null;

        if (lowestAmount != null) {
            isValid = false;
            do {
                String highestString = promptForString("Enter highest amount for range (or press Enter↵ to skip): ");
                if (highestString.isEmpty()) {
                    isValid = true; // skip
                } else {
                    try {
                        highestAmount = Double.parseDouble(highestString);
                        isValid = true; // input was valid and conversion was successful, exit loop
                    } catch (NumberFormatException e) {
                        System.out.println(UI.RED + "△ Invalid input. Please enter a valid number.\n" + UI.RESET);
                    } catch (Exception e) {
                        System.out.println(UI.RED + "△ Something went wrong. Please try again.\n" + UI.RESET);
                    }
                }
            } while (!isValid);
        }

        return new Double[]{lowestAmount, highestAmount};
    }
}