package br.com.comcet.tp1.symbol;

import java.util.Objects;

import br.com.comcet.tp1.ast.type.VariableType;

public class Symbol {
    private final String name;
    private final VariableType type;
    private Object value;

    public Symbol(String name, VariableType type, Object value) {
        Objects.requireNonNull(name, "Nome do símbolo não pode ser nulo.");
        Objects.requireNonNull(type, "Tipo do símbolo não pode ser nulo.");

        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Symbol(String name, VariableType type) {
        this(name, type, null);
    }

    public String getName() {
        return name;
    }

    public VariableType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
