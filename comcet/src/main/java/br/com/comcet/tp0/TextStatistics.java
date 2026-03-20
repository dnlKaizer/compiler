package br.com.comcet.tp0;

public record TextStatistics(
    Integer numLetters,
    Integer numWords,
    Character mostFrequentLetter,
    String mostFrequentWord
) {

public static final TextStatistics EMPTY = new TextStatistics(0, 0, '\0', "");

    @Override
    public String toString() {
        if (this.equals(EMPTY)) {
            return "Texto vazio.";
        }

        return "Caracteres: " + numLetters + "\n" +
                "Frequente: " + mostFrequentLetter + "\n" +
                "Palavras: " + numWords + "\n" +
                "Frequente: " + mostFrequentWord;
    }

}
