package com.pluralsight;

import java.util.stream.Stream;

public class UI {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[38;5;217m";
    public static final String GREEN = "\u001B[38;5;158m";
    public static final String BLUE = "\u001B[38;5;153m";

    // displays at the app start
    public static int menuHome() {
        clearConsole();
        int selectedOption = Console.promptForInt(BLUE + """
                \n╔════════════════════════════════════════╗
                ║            ✦  art & coin  ✧            ║
                ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                ║  [1] ✦ add deposit                     ║
                ║  [2] ✧ make payment                    ║
                ║  [3] ⌑ go to ledger                    ║
                ║  [0] ✕ exit application                ║
                ╚════════════════════════════════════════╝
                Select an option [0-3]: """ + RESET);
        return selectedOption;
    }

    // displays after selecting option 1 in menuHome()
    public static void addDepositHeader() {
        clearConsole();
        System.out.println(GREEN + """
                \n╔════════════════════════════════════════╗
                ║           ✦  add deposit  ✦            ║
                ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                ╚════════════════════════════════════════╝""" + RESET);
    }

    //displays after successfully adding deposit
    public static int addAnotherDeposit() {
        clearConsole();
        int selectedOption = Console.promptForInt(
                GREEN + "\n✦  deposit successfully added  ✦\n\n" + RESET + """
                        ╔════════════════════════════════════════╗
                        ║        ✦  add another payment  ✦       ║
                        ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                        ║  [1] ✚ add another deposit             ║
                        ║  [2] ↩ return to home screen           ║
                        ╚════════════════════════════════════════╝
                        Select an option [1-2]: """);
        return selectedOption;
    }

    // displays after selecting option 2 in menuHome()
    public static void makePayment() {
        clearConsole();
        System.out.println(RED + """
                \n╔════════════════════════════════════════╗
                ║           ✧  make payment  ✧           ║
                ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                ╚════════════════════════════════════════╝""" + RESET);
    }

    //displays after successfully adding
    public static int addAnotherPayment() {
        clearConsole();

        int selectedOption = Console.promptForInt(
                RED + "\n✧  payment successfully made  ✧\n\n" + RESET + """
                        ╔════════════════════════════════════════╗
                        ║       ✧  make another payment  ✧       ║
                        ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                        ║  [1] ✚ add another payment             ║
                        ║  [2] ↩ return to home screen           ║
                        ╚════════════════════════════════════════╝
                        Select an option [1-2]: """);
        return selectedOption;
    }

    // displays after selecting option 3 in menuHome()
    public static int menuLedger() {
        clearConsole();
        int selectedOption = Console.promptForInt(BLUE + """
                \n╔════════════════════════════════════════╗
                ║             ⌑  ledger  ⌑               ║
                ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                ║  [1] ▤ view all transactions           ║
                ║  [2] ✦ view deposits only              ║
                ║  [3] ✧ view payments only              ║
                ║  [4] ◫ view reports                    ║
                ║  [0] ↩ return to home screen           ║
                ╚════════════════════════════════════════╝
                Select an option [0-4]: """ + RESET);
        return selectedOption;
    }

    // displays after selecting option 4 in menuHome()
    public static int menuReports() {
        clearConsole();
        int selectedOption = Console.promptForInt(BLUE + """
                \n╔════════════════════════════════════════╗
                ║             ◫  reports  ◫              ║
                ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                ║  [1] ◫ month to date                   ║
                ║  [2] ◫ previous month                  ║
                ║  [3] ◫ year to date                    ║
                ║  [4] ◫ previous year                   ║
                ║  [5] ❖ search by vendor                ║
                ║  [6] ✎ custom search                   ║
                ║  [0] ↩ return to ledger                ║
                ╚════════════════════════════════════════╝
                Select an option [0-6]: """ + BLUE);
        return selectedOption;
    }

    // displays after selecting option 5 in menuReports()
    public static void searchByVendor() {
        clearConsole();
        System.out.println(BLUE + """
                \n╔════════════════════════════════════════╗
                ║  ✧  search transactions for vendor  ✧  ║
                ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                ╚════════════════════════════════════════╝""" + RESET);
    }

    // displays after selecting option 6 in menuReports()
    public static void customSearch() {
        clearConsole();
        System.out.println(BLUE + """
                \n╔════════════════════════════════════════╗    
                ║           ✧  custom search  ✧          ║
                ║      ~~~~~~~~~~~~~~~~~~~~~~~~~~~       ║
                ╚════════════════════════════════════════╝""" + RESET);
    }

    public static void tableHeader() {
        System.out.println("""
                ╔════════════╦══════════╦════════════════════════════════════════════════════╦═══════════════════════════╦════════════════════╗
                ║ ▦ date     ║ ◷ time   ║ ✎ description                                      ║ ❖ vendor                  ║        amount in $ ║
                ╠════════════╬══════════╬════════════════════════════════════════════════════╬═══════════════════════════╬════════════════════╣""");
    }

    // this method prints line breaks
    private static void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    // Source - https://medium.com/javarevisited/how-to-display-progressbar-on-the-standard-console-using-java-18f01d52b30e
    // Written by Arunprasadh C
    // Retrieved 2026-11-04
    public static void loadingBar(int length, long timeInterval) {
        char incomplete = '░'; // U+2591 Unicode Character
        char complete = '█'; // U+2588 Unicode Character
        StringBuilder builder = new StringBuilder();
        Stream.generate(() -> incomplete).limit(length).forEach(builder::append);
        System.out.println("\n\n\nloading...");
        for (int i = 0; i < length; i++) {
            builder.replace(i, i + 1, String.valueOf(complete));
            String progressBar = "\r" + builder;
            System.out.print(progressBar);
            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
