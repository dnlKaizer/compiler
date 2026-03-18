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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextStatistics that = (TextStatistics) o;

        if (!numLetters.equals(that.numLetters)) return false;
        if (!numWords.equals(that.numWords)) return false;
        if (!mostFrequentLetter.equals(that.mostFrequentLetter)) return false;
        return mostFrequentWord.equals(that.mostFrequentWord);
    }

    @Override
    public int hashCode() {
        int result = numLetters.hashCode();
        result = 31 * result + numWords.hashCode();
        result = 31 * result + mostFrequentLetter.hashCode();
        result = 31 * result + mostFrequentWord.hashCode();
        return result;
    }

}
