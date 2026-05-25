package br.com.comcet.tp1.ast.type;

import java.util.HashMap;

import br.com.comcet.tp1.token.Token;

public enum VariableType {
    STRING("string"), INTEGER("integer"), BOOLEAN("boolean"),
    VOID("void"), ERROR("error");

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

    @Override
    public String toString() {
        return this.name;
    }
    
}
