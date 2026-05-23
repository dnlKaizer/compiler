package br.com.comcet.tp1.ast.expression;

public abstract class Literal<T> extends Expression {
    private final T value;

    protected Literal(int line, int column, T value) {
        super(line, column);
        this.value = value;
    }

    // Construtor para testes
    protected Literal(T value) {
        this(1, 1, value);
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
