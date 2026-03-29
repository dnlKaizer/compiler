package br.com.comcet.tp2.scanner.identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.comcet.tp1.token.TokenType;

public class KeywordIdentifier {
    private static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("if", TokenType.IF);
        keywords.put("then", TokenType.THEN);
        keywords.put("else", TokenType.ELSE);
        
        keywords.put("while", TokenType.WHILE);
        keywords.put("do", TokenType.DO);

        keywords.put("writeln", TokenType.WRITELN);
        keywords.put("readln", TokenType.READLN);

        keywords.put("repeat", TokenType.REPEAT);
        keywords.put("until", TokenType.UNTIL);

        keywords.put("begin", TokenType.BEGIN);
        keywords.put("end", TokenType.END);

        keywords.put("var", TokenType.VAR);

        keywords.put("true", TokenType.BOOLEAN_LITERAL);
        keywords.put("false", TokenType.BOOLEAN_LITERAL);

        keywords.put("string", TokenType.STRING_TYPE);
        keywords.put("integer", TokenType.INTEGER_TYPE);
        keywords.put("boolean", TokenType.BOOLEAN_TYPE);
    }

    public static TokenType identify(String word) {
        Objects.requireNonNull(word, "Palavra-chave não pode ser nula.");

        String lowerCaseWord = word.toLowerCase();

        return Objects.requireNonNullElseGet(keywords.get(lowerCaseWord), () -> {
            throw new IllegalArgumentException("Palavra-chave não reconhecida: " + lowerCaseWord);
        });
    }

    public static boolean isKeyword(String word) {
        Objects.requireNonNull(word, "Palavra-chave não pode ser nula.");

        String lowerCaseWord = word.toLowerCase();

        return keywords.containsKey(lowerCaseWord);
    }

}
