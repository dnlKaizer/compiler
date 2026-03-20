package br.com.comcet.tp1.ast.type;

import java.util.Objects;

public enum Type {
    STRING("string"), INTEGER("integer"), BOOLEAN("boolean");

    private final String name;

    private Type(String name) {
        this.name = name;
    }

    public static Type inferType(Object value) {
        Objects.requireNonNull(value, "Valor não pode ser nulo.");
        if (value instanceof String) {
            return STRING;
        } else if (value instanceof Integer) {
            return INTEGER;
        } else if (value instanceof Boolean) {
            return BOOLEAN;
        } else {
            throw new IllegalArgumentException("Tipo de literal não suportado.");
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
