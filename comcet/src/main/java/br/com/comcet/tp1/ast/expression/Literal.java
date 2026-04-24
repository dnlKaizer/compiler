package br.com.comcet.tp1.ast.expression;

import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp1.token.Token;

public class Literal extends Expression {
    private final Object value;

    private Literal(int line, int column, Object value, VariableType type) {
        super(line, column, type);
        this.value = value;
    }

    public Literal(Token token) {
        this(token.line(), token.column(), token.text(), VariableType.fromTokenType(token));
    }
    
    // Construtor para testes
    public Literal(Object value, VariableType type) {
        this(1, 1, value, type);
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (getReturnType() == VariableType.STRING) {
            return "\'" + value + "\'";
        }
        return value.toString();
    }

}
