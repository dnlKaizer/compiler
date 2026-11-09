package br.com.comcet.frontend.ast.declaration;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import br.com.comcet.frontend.ast.expression.Identifier;
import br.com.comcet.frontend.type.VariableType;

public class VariableDeclaration extends Declaration {
    private final List<Identifier> identifiers;
    private final VariableType type;

    public VariableDeclaration(int line, int column, List<Identifier> identifiers, VariableType type) {
        super(line, column);

        Objects.requireNonNull(identifiers, "Lista de identificadores não pode ser nula.");
        identifiers.forEach(id -> Objects.requireNonNull(id, "Identificador na lista não pode ser nulo."));
        Objects.requireNonNull(type, "Tipo da variável não pode ser nulo.");

        this.identifiers = identifiers;
        this.type = type;
    }

    // Construtor para testes
    public VariableDeclaration(List<Identifier> identifiers, VariableType type) {
        this(1, 1, identifiers, type);
    }

    public List<Identifier> getIdentifiers() {
        return Collections.unmodifiableList(identifiers);
    }

    public VariableType getType() {
        return type;
    }

    @Override
    public void accept(DeclarationVisitor visitor) {
        visitor.visitVariableDeclaration(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < identifiers.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(identifiers.get(i).toString());
        }
        sb.append(": ").append(type.toString()).append(";");
        return sb.toString();
    }

}
