package br.com.comcet.tp2.scanner.lexical;

public interface LexicalRules {

    public boolean isWhitespace(char c);
    public boolean isLineBreak(char c);
    public boolean isEndOfFile(char c);
    public boolean isStringDelimiter(char c);

    public boolean isEscapeChar(char first, char second);
    public char convertEscapeChar(char first, char second);

    public boolean isIdentifierStart(char c);
    public boolean isIdentifierPart(char c);
    public boolean isDigit(char c);

    public boolean isSingleLineCommentStart(char first, char second);
    public boolean isBlockCommentStart(char c);
    public boolean isBlockCommentEnd(char c);

}
