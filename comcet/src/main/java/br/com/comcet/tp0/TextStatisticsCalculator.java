package br.com.comcet.tp0;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.comcet.utils.TextCleaner;

public class TextStatisticsCalculator {

    public static TextStatistics calculate(String text) {
        String[] words = TextCleaner.splitToWords(text);
        int numWords = words.length;

        if (numWords == 0) return TextStatistics.EMPTY;

        int numLetters = 0;
        Map<String, Integer> wordFrequency = new HashMap<>();
        Map<Character, Integer> letterFrequency = new HashMap<>();

        for (int i = 0; i < numWords; i++) {
            String word = words[i];
            numLetters += word.length();
            int wordCount = updateCount(wordFrequency, word);
            if (wordCount != 1) continue;
            updateLetterFrequencyCount(letterFrequency, word);
        }

        return new TextStatistics(
                numLetters,
                numWords,
                getMostFrequent(letterFrequency),
                getMostFrequent(wordFrequency));
    }

    private static void updateLetterFrequencyCount(Map<Character, Integer> letterFrequency, String word) {
        Set<Character> uniqueLetters = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);

            boolean isNewLetter = uniqueLetters.add(character);
            if (isNewLetter) {
                updateCount(letterFrequency, character);
            }
        }
    }

    private static <T> int updateCount(Map<T, Integer> tFrequency, T tObject) {
        int count = tFrequency.getOrDefault(tObject, 0) + 1;
        tFrequency.put(tObject, count);
        return count;
    }

    private static <T extends Comparable<T>> T getMostFrequent(Map<T, Integer> tFrequency) {
        T mostFrequent = null;
        int biggestCount = -1;

        for (Map.Entry<T, Integer> e : tFrequency.entrySet()) {
            T object = e.getKey();
            int count = e.getValue();

            boolean isMoreFrequent = count > biggestCount;
            boolean isTieButLexicographicallySmaller = count == biggestCount && object.compareTo(mostFrequent) < 0;

            if (isMoreFrequent || isTieButLexicographicallySmaller) {
                biggestCount = count;
                mostFrequent = object;
            }
        }

        return mostFrequent;
    }

}
