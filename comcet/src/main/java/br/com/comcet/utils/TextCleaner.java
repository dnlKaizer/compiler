package br.com.comcet.utils;

import java.util.Objects;

public class TextCleaner {
    public static String[] splitToWords(String text) {
        String cleanedText = cleanText(text);
        if (cleanedText.isEmpty()) return new String[0];
        return cleanedText.split("\\s+");
    }

    public static String cleanText(String text) {
        Objects.requireNonNull(text, "O texto não pode ser nulo.");
        return text.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").trim();
    }

}
