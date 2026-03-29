package br.com.comcet.tp2.scanner.lexical;

public class PascalLexicalRules implements LexicalRules {

    private static final char EOF = '\0';
    private static final char STRING_DELIMITER = '\'';
    private static final char BLOCK_COMMENT_START = '{';
    private static final char BLOCK_COMMENT_END = '}';
    private static final char SINGLE_LINE_COMMENT = '/';
    private static final char ESCAPE_CHAR = '\\';
    private static final char IDENTIFIER_START = '_';
    private static final char[] WHITESPACE_CHARS = {' ', '\t', '\r', '\n'};

    @Override
    public boolean isBlockCommentEnd(char c) {
        return c == BLOCK_COMMENT_END;
    }

    @Override
    public boolean isBlockCommentStart(char c) {
        return c == BLOCK_COMMENT_START;
    }

    @Override
    public boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    @Override
    public boolean isEndOfFile(char c) {
        return c == EOF;
    }

    @Override
    public boolean isEscapeChar(char first, char second) {
        if (first != ESCAPE_CHAR) {
            return false;
        }
        return second == STRING_DELIMITER
            || second == ESCAPE_CHAR
            || second == 'n'
            || second == 'r'
            || second == 't';
    }

    @Override
    public char convertEscapeChar(char first, char second) {
        if (!isEscapeChar(first, second)) {
            throw new IllegalArgumentException("Caractere de escape inválido: " + first + second);
        }
        switch (second) {
            case STRING_DELIMITER:
                return STRING_DELIMITER;
            case ESCAPE_CHAR:
                return ESCAPE_CHAR;
            case 'n':
                return '\n';
            case 'r':
                return '\r';
            case 't':
                return '\t';
            default:
                throw new IllegalArgumentException("Caractere de escape inválido: " + second);
        }
    }

    @Override
    public boolean isIdentifierPart(char c) {
        return Character.isLetterOrDigit(c) || c == IDENTIFIER_START;
    }

    @Override
    public boolean isIdentifierStart(char c) {
        return Character.isLetter(c) || c == IDENTIFIER_START;
    }

    @Override
    public boolean isLineBreak(char c) {
        return c == '\n';
    }

    @Override
    public boolean isSingleLineCommentStart(char first, char second) {
        return first == SINGLE_LINE_COMMENT && second == SINGLE_LINE_COMMENT;
    }

    @Override
    public boolean isStringDelimiter(char c) {
        return c == STRING_DELIMITER;
    }

    @Override
    public boolean isWhitespace(char c) {
        for (char whitespaceChar : WHITESPACE_CHARS) {
            if (c == whitespaceChar) {
                return true;
            }
        }
        return false;
    }
    
}
