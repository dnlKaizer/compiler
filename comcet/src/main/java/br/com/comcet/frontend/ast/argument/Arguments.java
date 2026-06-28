package br.com.comcet.frontend.ast.argument;

import java.util.List;
import java.util.Objects;

import br.com.comcet.frontend.ast.AstNode;
import br.com.comcet.frontend.ast.expression.Expression;

public class Arguments extends AstNode {
    private final List<Expression> arguments;

    public Arguments(int line, int column, List<Expression> arguments) {
        super(line, column);
        Objects.requireNonNull(arguments, "Lista de argumentos não pode ser nula.");
        arguments.forEach(arg -> Objects.requireNonNull(arg, "Argumento não pode ser nulo."));
        this.arguments = arguments;
    }

    public List<Expression> getExpressions() {
        return arguments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Expression arg : arguments) {
            sb.append(arg.toString()).append(", ");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 2);
        return sb.toString();
    }
    
}
