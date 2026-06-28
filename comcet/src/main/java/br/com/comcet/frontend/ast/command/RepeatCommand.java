package br.com.comcet.frontend.ast.command;

import java.util.Objects;

import br.com.comcet.frontend.ast.expression.Expression;

public class RepeatCommand extends Command {
    private final Expression condition;
    private final Command body;

    public RepeatCommand(int line, int column, Expression condition, Command body) {
        super(line, column);

        Objects.requireNonNull(condition, "Condição não pode ser nula.");
        Objects.requireNonNull(body, "Corpo do loop não pode ser nulo.");

        this.condition = condition;
        this.body = body;
    }

    // Construtor para testes
    public RepeatCommand(Expression condition, Command body) {
        this(1, 1, condition, body);
    }

    public Expression getCondition() {
        return condition;
    }

    public Command getBody() {
        return body;
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;
        StringBuilder sb = new StringBuilder();

        sb.append("repeat")
            .append(br).append(IndentationUtil.indentCommand(body))
            .append(br).append("until (").append(condition.toString()).append(")")
            .append(br);
        
        return sb.toString();
    }
}
