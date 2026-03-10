package br.com.comcet.tp0;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class TextStatisticsCalculator {

    private record TextStatistics(
            Integer numLetters,
            Integer numWords,
            Character mostFrequentLetter,
            String mostFrequentWord) {
    }

    public static String calculateStatistics(String text) {
        Objects.requireNonNull(text, "O texto de entrada não pode ser nulo.");

        TextStatistics textStatistics = calculate(text);
        if (textStatistics == null)
            return "Texto vazio.";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Caracteres: ")
                .append(textStatistics.numLetters)
                .append("\n")

                .append("Frequente: ")
                .append(textStatistics.mostFrequentLetter)
                .append("\n")

                .append("Palavras: ")
                .append(textStatistics.numWords)
                .append("\n")

                .append("Frequente: ")
                .append(textStatistics.mostFrequentWord);

        return stringBuilder.toString();
    }

    private static TextStatistics calculate(String text) {
        String cleanedText = text.toLowerCase().replaceAll("[^a-zA-Z\\s]", "").trim();
        if (cleanedText.isEmpty())
            return null;

        String[] words = cleanedText.split("\\s+");

        int numLetters = 0;
        for (String word : words) {
            numLetters += word.length();
        }
        int numWords = words.length;

        Map<String, Integer> wordFrequency = new HashMap<>();
        Map<Character, Integer> letterFrequency = new HashMap<>();
        updateWordAndLetterFrequencyCount(wordFrequency, letterFrequency, words);

        return new TextStatistics(
                numLetters,
                numWords,
                getMostFrequent(letterFrequency),
                getMostFrequent(wordFrequency));
    }

    private static void updateWordAndLetterFrequencyCount(Map<String, Integer> wordFrequency,
            Map<Character, Integer> letterFrequency, String[] words) {
        for (String word : words) {
            updateCount(wordFrequency, word);
            updateLetterFrequencyCount(letterFrequency, word);
        }
    }

    private static void updateLetterFrequencyCount(Map<Character, Integer> letterFrequency, String word) {
        Set<Character> uniqueLetters = new HashSet<>();
        for (char character : word.toCharArray()) {
            uniqueLetters.add(character);
        }
        for (char character : uniqueLetters) {
            updateCount(letterFrequency, character);
        }
    }

    private static <T> void updateCount(Map<T, Integer> tFrequency, T tObject) {
        int count = tFrequency.getOrDefault(tObject, 0);
        tFrequency.put(tObject, count + 1);
    }

    private static <T extends Comparable<T>> T getMostFrequent(Map<T, Integer> tFrequency) {
        T mostFrequent = null;
        int biggestCount = -1;

        for (Map.Entry<T, Integer> e : tFrequency.entrySet()) {
            T object = e.getKey();
            int count = e.getValue();

            if (count > biggestCount || (count == biggestCount && object.compareTo(mostFrequent) < 0)) {
                biggestCount = count;
                mostFrequent = object;
            }
        }

        return mostFrequent;
    }

}
