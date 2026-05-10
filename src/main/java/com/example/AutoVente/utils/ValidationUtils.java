package com.example.AutoVente.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    // Validation email
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Validation téléphone (Sénégal)
    private static final String PHONE_REGEX = "^(77|78|70|76)[0-9]{7}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    // Validation plaque d'immatriculation
    private static final String PLATE_REGEX = "^[A-Z]{2}-[0-9]{3}-[A-Z]{2}$";
    private static final Pattern PLATE_PATTERN = Pattern.compile(PLATE_REGEX);

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidPlateNumber(String plate) {
        return plate != null && PLATE_PATTERN.matcher(plate).matches();
    }

    public static boolean isValidYear(Integer year) {
        return year != null && year >= 1900 && year <= 2025;
    }

    public static boolean isPositiveDouble(Double value) {
        return value != null && value > 0;
    }

    public static boolean isPositiveInteger(Integer value) {
        return value != null && value >= 0;
    }

    public static String sanitizeString(String input) {
        if (input == null) return null;
        return input.trim().replaceAll("\\s+", " ");
    }

    public static String truncateString(String input, int maxLength) {
        if (input == null) return null;
        if (input.length() <= maxLength) return input;
        return input.substring(0, maxLength - 3) + "...";
    }

    public static Double formatPrice(Double price) {
        if (price == null) return 0.0;
        return Math.round(price * 100.0) / 100.0;
    }
}