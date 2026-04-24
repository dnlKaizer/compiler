package br.com.comcet.tp1.ast.expression;

import java.util.Objects;

import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp1.symbol.Symbol;
import br.com.comcet.tp1.token.Token;

public class Identifier extends Expression {
    private final String name;

    public Identifier(int line, int column, String name, VariableType returnType) {
        super(line, column, returnType);
        Objects.requireNonNull(name, "Nome do identificador não pode ser nulo.");
        this.name = name;
    }

    public Identifier(Token token, Symbol symbol) {
        this(token.line(), token.column(), symbol.getName(), symbol.getType());
    }

    // Construtor para testes
    public Identifier(String name, VariableType returnType) {
        this(1, 1, name, returnType);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
