package com.pluralsight;

// Source - https://stackoverflow.com/a/1001340
// Posted by laalto, modified by community. See post 'Timeline' for change history
// Retrieved 2026-11-04, License - CC BY-SA 2.5

import java.io.PrintStream;
import java.util.stream.Stream;

public class LoadingBar {
    public static void print(int length, long timeInterval) {
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
