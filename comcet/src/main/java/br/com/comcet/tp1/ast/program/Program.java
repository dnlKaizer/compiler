package br.com.comcet.tp1.ast.program;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import br.com.comcet.tp1.ast.AstNode;
import br.com.comcet.tp1.ast.command.Command;
import br.com.comcet.tp1.ast.command.IndentationUtil;
import br.com.comcet.tp1.ast.declaration.VariableDeclaration;

public class Program extends AstNode {
    private final List<VariableDeclaration> declarations;
    private final Command body;

    public Program(int line, int column, List<VariableDeclaration> declarations, Command body) {
        super(line, column);

        Objects.requireNonNull(declarations, "Lista de declarações não pode ser nula.");
        declarations.forEach(declaration ->
            Objects.requireNonNull(declaration, "Declaração não pode ser nula.")
        );
        Objects.requireNonNull(body, "Corpo principal não pode ser nulo.");

        this.declarations = declarations;
        this.body = body;
    }

    // Construtor para testes
    public Program(List<VariableDeclaration> declarations, Command body) {
        this(1, 1, declarations, body);
    }

    public List<VariableDeclaration> getDeclarations() {
        return Collections.unmodifiableList(declarations);
    }

    public Command getBody() {
        return body;
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;
        StringBuilder sb = new StringBuilder();

        sb.append("var").append(br);
        for (VariableDeclaration declaration : declarations) {
            sb.append(IndentationUtil.indent(declaration.toString() + ";")).append(br);
        }

        sb.append(body.toString());
        return sb.toString();
    }
}
