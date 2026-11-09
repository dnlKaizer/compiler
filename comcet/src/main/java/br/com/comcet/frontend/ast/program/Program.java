package br.com.comcet.frontend.ast.program;

import java.util.Objects;
import java.util.Stack;

import br.com.comcet.frontend.ast.AstNode;
import br.com.comcet.frontend.ast.NodeList;
import br.com.comcet.frontend.ast.command.BlockCommand;
import br.com.comcet.frontend.ast.command.IndentationUtil;
import br.com.comcet.frontend.ast.declaration.SubroutineDeclaration;
import br.com.comcet.frontend.ast.declaration.VariableDeclaration;
import br.com.comcet.frontend.exception.CompilerException;

public class Program extends AstNode {
    private final NodeList<VariableDeclaration> variableDeclarations;
    private final NodeList<SubroutineDeclaration> subroutineDeclarations;
    private final BlockCommand body;
    private final String name;
    private final Stack<CompilerException> stack;

    public Program(int line, int column, NodeList<VariableDeclaration> variableDeclarations,
            NodeList<SubroutineDeclaration> subroutineDeclarations, BlockCommand body, String name, Stack<CompilerException> stack) {
        super(line, column);

        Objects.requireNonNull(variableDeclarations, "Lista de declarações não pode ser nula.");
        Objects.requireNonNull(subroutineDeclarations, "Lista de sub-rotinas não pode ser nula.");
        Objects.requireNonNull(body, "Corpo principal não pode ser nulo.");

        this.variableDeclarations = variableDeclarations;
        this.subroutineDeclarations = subroutineDeclarations;
        this.body = body;
        this.name = name;
        this.stack = stack;
    }

    // Construtor para testes
    public Program(NodeList<VariableDeclaration> variableDeclarations, NodeList<SubroutineDeclaration> subroutineDeclarations, BlockCommand body) {
        this(1, 1, variableDeclarations, subroutineDeclarations, body, "UnnamedProgram", null);
    }

    public NodeList<VariableDeclaration> getDeclarations() {
        return variableDeclarations;
    }

    public NodeList<SubroutineDeclaration> getSubroutineDeclarations() {
        return subroutineDeclarations;
    }

    public BlockCommand getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;
        StringBuilder sb = new StringBuilder();

        sb.append("program ").append(name).append(";").append(br).append(br);
        sb.append(variableDeclarations.toString());
        sb.append(subroutineDeclarations.toString());
        sb.append(body.toString()).append(".");

        if (stack != null && !stack.isEmpty()) {
            sb.append(br).append("Parser Exceptions:").append(br);
            for (CompilerException e : stack) {
                sb.append(IndentationUtil.indent(e.getMessage())).append(br);
            }
        }

        return sb.toString();
    }
}
