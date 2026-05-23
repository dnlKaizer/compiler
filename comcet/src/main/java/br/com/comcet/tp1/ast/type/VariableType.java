package br.com.comcet.tp1.ast.type;

import java.util.HashMap;

import br.com.comcet.tp1.token.Token;

public enum VariableType {
    STRING("string"), INTEGER("integer"), BOOLEAN("boolean");

    private static final HashMap<String, VariableType> BY_STRING = new HashMap<>();
    static {
        for (VariableType vt : values()) {
            BY_STRING.put(vt.name, vt);
        }
    }

    private final String name;

    private VariableType(String name) {
        this.name = name;
    }

    public static VariableType fromTokenType(Token token) {
        switch (token.type()) {
            case STRING_LITERAL, STRING_TYPE:
                return STRING;
            case INTEGER_LITERAL, INTEGER_TYPE:
                return INTEGER;
            case BOOLEAN_LITERAL, BOOLEAN_TYPE:
                return BOOLEAN;
            default:
                throw new IllegalArgumentException("Token não é um literal válido para inferência de tipo.");
        }
    }

    public static VariableType fromString(String str) {
        return BY_STRING.get(str.toLowerCase());
    }

    public boolean supportsOperations() {
        return supportsArithmetic() || supportsComparison() || supportsLogicalBinary() || supportsEquality();
    }

    public boolean supportsArithmetic() {
        return this == INTEGER;
    }

    public boolean supportsComparison() {
        return this == INTEGER;
    }

    public boolean supportsLogicalBinary() {
        return this == BOOLEAN;
    }

    public boolean supportsEquality() {
        return this == STRING || this == INTEGER || this == BOOLEAN;
    }

    public boolean isCompatibleWith(VariableType other) {
        return this == other;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
