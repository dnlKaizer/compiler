package br.com.comcet.frontend.type;

import java.util.HashMap;

public enum OperatorType {
    // Arithmetic
    MULT("*", 1),
    DIV("/", 1),

    SUM("+", 2),
    SUB("-", 2),

    // Logical
    NOT("not", 0),

    AND("and", 1),

    OR("or", 2),
    XOR("xor", 2),

    // Relational
    EQUAL("=", 3),
    NOT_EQUAL("<>", 3),
    GREATER_THAN(">", 3),
    LESS_THAN("<", 3),
    GREATER_EQUAL(">=", 3),
    LESS_EQUAL("<=", 3);

    private static final HashMap<String, OperatorType> BY_STRING = new HashMap<>();
    static {
        for (OperatorType op : values()) {
            BY_STRING.put(op.symbol, op);
        }
    }

    private final String symbol;
    private final int precedence;

    private OperatorType(String symbol, int precedence) {
        this.symbol = symbol;
        this.precedence = precedence;
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

    public VariableType getResultType(VariableType type) {
        if (!supportsUnaryOperation(type)) {
            throw new IllegalArgumentException("Operação unária inválida para o tipo fornecido: " + type);
        }

        return switch (this) {
            case NOT -> VariableType.BOOLEAN;
            default -> throw new IllegalStateException("Operador desconhecido: " + this);
        };
    }

    public VariableType getResultType(VariableType leftType, VariableType rightType) {
        if (!supportsBinaryOperation(leftType, rightType)) {
            throw new IllegalArgumentException("Operação binária inválida para os tipos fornecidos: " + leftType + ", " + rightType);
        }

        return switch (this) {
            case MULT, DIV, SUM, SUB -> leftType; // Retorna o mesmo tipo do operando
            case AND, OR, XOR -> VariableType.BOOLEAN;
            case EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL -> VariableType.BOOLEAN;
            default -> throw new IllegalStateException("Operador desconhecido: " + this);
        };
    }

    public boolean isRelational() {
        return switch (this) {
            case EQUAL, NOT_EQUAL, GREATER_THAN, LESS_THAN, GREATER_EQUAL, LESS_EQUAL -> true;
            default -> false;
        };
    }

    public static OperatorType fromString(String symbol) {
        return BY_STRING.get(symbol);
    }

    @Override
    public String toString() {
        return this.symbol;
    }

}
