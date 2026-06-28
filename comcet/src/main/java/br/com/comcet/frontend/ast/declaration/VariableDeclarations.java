package br.com.comcet.frontend.ast.declaration;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import br.com.comcet.frontend.ast.command.IndentationUtil;

public class VariableDeclarations extends Declaration {
    private final List<VariableDeclaration> variableDeclarations;

    public VariableDeclarations(int line, int column, List<VariableDeclaration> variableDeclarations) {
        super(line, column);

        Objects.requireNonNull(variableDeclarations, "Lista de declarações de variáveis não pode ser nula.");
        variableDeclarations.forEach(varDecl -> Objects.requireNonNull(varDecl, "Declaração de variável na lista não pode ser nula."));

        this.variableDeclarations = variableDeclarations;
    }

    public VariableDeclarations(List<VariableDeclaration> variableDeclarations) {
        this(1, 1, variableDeclarations);
    }

    public List<VariableDeclaration> getVariableDeclarations() {
        return Collections.unmodifiableList(variableDeclarations);
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;
        StringBuilder sb = new StringBuilder();
        if (variableDeclarations.isEmpty()) {
            return sb.toString();
        }
        sb.append("var").append(br);
        for (VariableDeclaration declaration : variableDeclarations) {
            sb.append(IndentationUtil.indent(declaration.toString())).append(br);
        }
        sb.append(br);
        return sb.toString();
    }

}
