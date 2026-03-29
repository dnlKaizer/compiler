package br.com.comcet.tp2.scanner.identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import br.com.comcet.tp1.token.TokenType;

public class SymbolIdentifier {
    private static final Map<String, TokenType> symbols = new HashMap<>();

    static {
        // Símbolos de pontuação
        symbols.put("(", TokenType.LPAREN);
        symbols.put(")", TokenType.RPAREN);
        symbols.put(";", TokenType.SEMICOLON);
        symbols.put(",", TokenType.COMMA);

        // Operadores aritméticos
        symbols.put("+", TokenType.SUM);
        symbols.put("-", TokenType.SUB);
        symbols.put("*", TokenType.MULT);
        symbols.put("/", TokenType.DIV);

        // Operadores de comparação
        symbols.put("=", TokenType.EQUAL);
        symbols.put("<>", TokenType.NOT_EQUAL);
        symbols.put("<", TokenType.LESS_THAN);
        symbols.put("<=", TokenType.LESS_EQUAL);
        symbols.put(">", TokenType.GREATER_THAN);
        symbols.put(">=", TokenType.GREATER_EQUAL);

        // Operador de atribuição
        symbols.put(":=", TokenType.ASSIGN);
        symbols.put(":", TokenType.COLON);
    }
    
    public static TokenType identify(String symbol) {
        Objects.requireNonNull(symbol, "Símbolo não pode ser nulo.");

        return Objects.requireNonNullElseGet(symbols.get(symbol), () -> {
            throw new IllegalArgumentException("Símbolo não reconhecido: " + symbol);
        });
    }

    public static boolean isSymbol(String character) {
        Objects.requireNonNull(character, "Caractere não pode ser nulo.");
        
        return symbols.containsKey(character);
    }

}
