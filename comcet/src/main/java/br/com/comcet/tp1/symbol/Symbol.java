package br.com.comcet.tp1.symbol;

import java.util.Objects;

import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.type.Type;

public class Symbol {
    private final Identifier identifier;
    private final Type type;
    private Object value;

    public Symbol(Identifier identifier, Type type, Object value) {
        Objects.requireNonNull(identifier, "Identificador não pode ser nulo.");
        Objects.requireNonNull(type, "Tipo do símbolo não pode ser nulo.");

        this.identifier = identifier;
        this.type = type;
        this.value = value;
    }

    public Symbol(Identifier identifier, Type type) {
        this(identifier, type, null);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public String getName() {
        return identifier.getName();
    }

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
