package br.com.comcet.tp1.ast.command;

import java.util.Objects;

import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Identifier;

public class AssignmentCommand extends Command {
    private final Identifier id;
    private final Expression expression;

    public AssignmentCommand(int line, int column, Identifier id, Expression expression) {
        super(line, column);

        Objects.requireNonNull(id, "Identificador não pode ser nulo.");
        Objects.requireNonNull(expression, "Expressão não pode ser nula.");

        this.id = id;
        this.expression = expression;
    }

    // Construtor para testes
    public AssignmentCommand(Identifier id, Expression expression) {
        this(1, 1, id, expression);
    }

    public Identifier getId() {
        return id;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id.toString()).append(" := ").append(expression.toString());
        return sb.toString();
    }

}
