package br.com.comcet.frontend.ast.expression;

import java.util.Objects;

public class Identifier extends Expression {
    private final String name;

    public Identifier(int line, int column, String name) {
        super(line, column);
        Objects.requireNonNull(name, "Nome do identificador não pode ser nulo.");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitIdentifier(this);
    }

    @Override
    public String toString() {
        return name;
    }

}
