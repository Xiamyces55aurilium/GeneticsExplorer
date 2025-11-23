package com.yourname.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utility class providing helper functions used across the project.
 */
public class Helper {

    /**
     * Sorts the characters of a string alphabetically.
     * Example: sortAlleles("aA") returns "Aa"
     *
     * @param input String to sort
     * @return Alphabetically sorted string
     */
    public static String sortAlleles(String input) {
        char[] arr = input.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }

    /**
     * Formats a percent value to 2 decimal places as a String.
     *
     * @param value double value to format
     * @return formatted string
     */
    public static String formatPercent(double value) {
        return String.format("%.2f%%", value);
    }

    /**
     * Joins an array of strings with commas.
     *
     * @param items Array of string items
     * @return Comma-separated string
     */
    public static String joinWithComma(String[] items) {
        return Arrays.stream(items).collect(Collectors.joining(", "));
    }
}
