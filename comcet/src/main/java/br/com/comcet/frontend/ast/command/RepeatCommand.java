package br.com.comcet.frontend.ast.command;

import java.util.List;
import java.util.Objects;

import br.com.comcet.frontend.ast.NodeList;
import br.com.comcet.frontend.ast.expression.Expression;

public class RepeatCommand extends Command {
    private final Expression condition;
    private final NodeList<Command> body;

    public RepeatCommand(int line, int column, Expression condition, NodeList<Command> body) {
        super(line, column);

        Objects.requireNonNull(condition, "Condição não pode ser nula.");
        Objects.requireNonNull(body, "Corpo do loop não pode ser nulo.");

        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Command> getBody() {
        return body.getItems();
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitRepeatCommand(this);
    }

    // @Override
    // public String toString() {
    //     String br = IndentationUtil.NEWLINE;
    //     StringBuilder sb = new StringBuilder();

    //     sb.append("repeat")
    //         .append(br).append(IndentationUtil.indentCommand(body))
    //         .append(br).append("until (").append(condition.toString()).append(")")
    //         .append(br);
        
    //     return sb.toString();
    // }
}
