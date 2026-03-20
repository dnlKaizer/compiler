package br.com.comcet.tp1.ast.command;

import java.util.Objects;

import br.com.comcet.tp1.ast.expression.Expression;

public class IfCommand extends Command {
    private final Expression condition;
    private final Command thenBranch;
    private final Command elseBranch;
    
    public IfCommand(int line, int column, Expression condition, Command thenBranch, Command elseBranch) {
        super(line, column);

        Objects.requireNonNull(condition, "Condição não pode ser nula.");
        Objects.requireNonNull(thenBranch, "Bloco 'then' não pode ser nulo.");
        // elseBranch pode ser nulo, pois é opcional

        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    // Construtor para testes
    public IfCommand(Expression condition, Command thenBranch, Command elseBranch) {
        this(1, 1, condition, thenBranch, elseBranch);
    }

    public Expression getCondition() {
        return condition;
    }

    public Command getThenBranch() {
        return thenBranch;
    }

    public Command getElseBranch() {
        return elseBranch;
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;

        StringBuilder sb = new StringBuilder();
        sb.append("if (").append(condition.toString()).append(") then")
            .append(br).append(IndentationUtil.indentCommand(thenBranch))
            .append(br);

        if (elseBranch != null) {
            sb.append("else")
                .append(br).append(IndentationUtil.indentCommand(elseBranch))
                .append(br);
        }

        return sb.toString();
    }

}
