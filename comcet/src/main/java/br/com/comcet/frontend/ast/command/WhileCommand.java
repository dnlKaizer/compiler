package br.com.comcet.frontend.ast.command;

import java.util.Objects;

import br.com.comcet.frontend.ast.expression.Expression;

public class WhileCommand extends Command {
    private final Expression condition;
    private final Command body;

    public WhileCommand(int line, int column, Expression condition, Command body) {
        super(line, column);

        Objects.requireNonNull(condition, "Condição não pode ser nula.");
        Objects.requireNonNull(body, "Corpo do loop não pode ser nulo.");

        this.condition = condition;
        this.body = body;
    }

    // Construtor para testes
    public WhileCommand(Expression condition, Command body) {
        this(1, 1, condition, body);
    }

    public Expression getCondition() {
        return condition;
    }

    public Command getBody() {
        return body;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitWhileCommand(this);
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;
        StringBuilder sb = new StringBuilder();

        sb.append("while (").append(condition.toString()).append(") do")
            .append(br).append(IndentationUtil.indentCommand(body))
            .append(br);
        
        return sb.toString();
    }

}
