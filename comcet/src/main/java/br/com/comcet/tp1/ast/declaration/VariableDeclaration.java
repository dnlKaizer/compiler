package br.com.comcet.tp1.ast.declaration;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import br.com.comcet.tp1.ast.command.Command;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.type.Type;

public class VariableDeclaration extends Command {
    private final List<Identifier> identifiers;
    private final Type type;

    public VariableDeclaration(int line, int column, List<Identifier> identifiers, Type type) {
        super(line, column);

        Objects.requireNonNull(identifiers, "Lista de identificadores não pode ser nula.");
        Objects.requireNonNull(type, "Tipo de variável não pode ser nulo.");
        identifiers.forEach(id -> Objects.requireNonNull(id, "Identificador na lista não pode ser nulo."));

        this.identifiers = identifiers;
        this.type = type;
    }

    // Construtor para testes
    public VariableDeclaration(List<Identifier> identifiers, Type type) {
        this(1, 1, identifiers, type);
    }

    public List<Identifier> getIdentifiers() {
        return Collections.unmodifiableList(identifiers);
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < identifiers.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(identifiers.get(i).toString());
        }
        sb.append(" : ").append(type.toString());
        return sb.toString();
    }

}
