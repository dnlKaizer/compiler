package br.com.comcet.tp1.ast.type;

import java.util.EnumMap;
import java.util.HashMap;

import br.com.comcet.tp1.token.TokenType;

public enum OperatorType {
    // Arithmetic
    MULT("*", 1, TokenType.MULT),
    DIV("/", 1, TokenType.DIV),

    SUM("+", 2, TokenType.SUM),
    SUB("-", 2, TokenType.SUB),

    // Logical
    NOT("not", 0, TokenType.NOT),

    AND("and", 1, TokenType.AND),

    OR("or", 2, TokenType.OR),
    XOR("xor", 2, TokenType.XOR),

    // Relational
    EQUAL("=", 3, TokenType.EQUAL),
    NOT_EQUAL("<>", 3, TokenType.NOT_EQUAL),
    GREATER_THAN(">", 3, TokenType.GREATER_THAN),
    LESS_THAN("<", 3, TokenType.LESS_THAN),
    GREATER_EQUAL(">=", 3, TokenType.GREATER_EQUAL),
    LESS_EQUAL("<=", 3, TokenType.LESS_EQUAL);

    private static final EnumMap<TokenType, OperatorType> BY_TOKEN = new EnumMap<>(TokenType.class);
    private static final HashMap<String, OperatorType> BY_STRING = new HashMap<>();
    static {
        for (OperatorType op : values()) {
            BY_TOKEN.put(op.tokenType, op);
            BY_STRING.put(op.symbol, op);
        }
    }

    private final String symbol;
    private final int precedence;
    private final TokenType tokenType;

    private OperatorType(String symbol, int precedence, TokenType tokenType) {
        this.symbol = symbol;
        this.precedence = precedence;
        this.tokenType = tokenType;
    }

    public int getPrecedence() {
        return precedence;
    }

    public boolean isArithmetic() {
        return this == SUM || this == SUB || this == MULT || this == DIV;
    }

    public boolean isRelational() {
        return this == NOT_EQUAL || this == EQUAL || isComparison();
    }

    public boolean isEquality() {
        return this == NOT_EQUAL || this == EQUAL;
    }

    public boolean isComparison() {
        return this == LESS_THAN || this == GREATER_THAN ||
                this == GREATER_EQUAL || this == LESS_EQUAL;
    }

    public boolean isLogicalBinary() {
        return this == AND || this == OR || this == XOR;
    }

    public static OperatorType fromTokenType(TokenType tokenType) {
        return BY_TOKEN.get(tokenType);
    }

    public static OperatorType fromString(String symbol) {
        return BY_STRING.get(symbol);
    }

    public static boolean isOperator(TokenType tokenType) {
        return BY_TOKEN.containsKey(tokenType);
    }

    @Override
    public String toString() {
        return this.symbol;
    }

}
