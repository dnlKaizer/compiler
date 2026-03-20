package br.com.comcet.tp1.ast.expression;

import java.util.Objects;

public class Identifier extends Expression {
    private final String name;

    public Identifier(int line, int column, String name) {
        super(line, column);
        Objects.requireNonNull(name, "Nome do identificador não pode ser nulo.");
        this.name = name;
    }

    // Construtor para testes
    public Identifier(String name) {
        this(1, 1, name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
