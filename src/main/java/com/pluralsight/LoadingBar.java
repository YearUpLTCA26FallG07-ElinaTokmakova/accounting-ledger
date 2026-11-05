package com.pluralsight;

// Source - https://medium.com/javarevisited/how-to-display-progressbar-on-the-standard-console-using-java-18f01d52b30e
// Written by Arunprasadh C
// Retrieved 2026-11-04

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
