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

    public boolean supportsUnaryOperation(VariableType operandType) {
        if (this == NOT) {
            return operandType == VariableType.BOOLEAN;
        }
        return false;
    }

    public boolean supportsBinaryOperation(VariableType leftType, VariableType rightType) {
        boolean isSameType = leftType == rightType;
        return switch (this) {
            case MULT, DIV, SUM, SUB ->
                (isSameType && rightType == VariableType.INTEGER) ||
                (isSameType && rightType == VariableType.STRING && this == SUM);
            case AND, OR, XOR ->
                (isSameType && rightType == VariableType.BOOLEAN);
            case EQUAL, NOT_EQUAL -> (isSameType &&
                (rightType == VariableType.INTEGER || rightType == VariableType.BOOLEAN));
            case GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL ->
                (isSameType && rightType == VariableType.INTEGER);
            default -> false;
        };
    }

    public VariableType getReturnType() {
        return switch (this) {
            case MULT, DIV, SUM, SUB -> VariableType.INTEGER;
            case NOT, AND, OR, XOR, EQUAL, NOT_EQUAL, GREATER_THAN,
                LESS_THAN, GREATER_EQUAL, LESS_EQUAL -> VariableType.BOOLEAN;
            default -> throw new IllegalStateException("Operador desconhecido: " + this);
        };
    }

    public boolean isRelational() {
        return switch (this) {
            case EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL -> true;
            default -> false;
        };
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
