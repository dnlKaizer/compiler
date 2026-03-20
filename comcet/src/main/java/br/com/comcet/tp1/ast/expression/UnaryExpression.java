package br.com.comcet.tp1.ast.expression;

import java.util.Objects;

public class UnaryExpression extends Expression {
    private final Expression expression;
    private final String operator;

    public UnaryExpression(int line, int column, Expression expression, String operator) {
        super(line, column);

        Objects.requireNonNull(expression, "Expressão não pode ser nula.");
        Objects.requireNonNull(operator, "Operador não pode ser nulo.");

        this.expression = expression;
        this.operator = operator;
    }

    // Construtor para testes
    public UnaryExpression(Expression expression, String operator) {
        this(1, 1, expression, operator);
    }

    public Expression getExpression() {
        return expression;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(operator).append(" ").append(expression.toString());
        return sb.toString();
    }

}
