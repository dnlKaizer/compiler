package br.com.comcet.tp1.ast.command;

import java.util.Objects;

import br.com.comcet.tp1.ast.expression.Expression;

public class PrintCommand extends Command {
    private final Expression expression;

    public PrintCommand(int line, int column, Expression expression) {
        super(line, column);
        Objects.requireNonNull(expression, "Expressão não pode ser nula.");
        this.expression = expression;
    }

    // Construtor para testes
    public PrintCommand(Expression expression) {
        this(1, 1, expression);
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;
        StringBuilder sb = new StringBuilder();
        sb.append("write(").append(expression.toString()).append(");").append(br);
        return sb.toString();
    }

}
