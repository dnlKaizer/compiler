package br.com.comcet.tp1.ast.expression;

import br.com.comcet.tp1.ast.type.Type;

public class Literal extends Expression {
    private final Object value;
    private final Type type;

    public Literal(int line, int column, Object value) {
        super(line, column);
        this.type = Type.inferType(value);
        this.value = value;
    }
    
    // Construtor para testes
    public Literal(Object value) {
        this(1, 1, value);
    }

    public Object getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
