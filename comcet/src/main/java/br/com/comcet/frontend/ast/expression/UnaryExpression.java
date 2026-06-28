package br.com.comcet.frontend.ast.expression;

import java.util.Objects;

import br.com.comcet.frontend.type.OperatorType;

public class UnaryExpression extends Expression {
    private final Expression expression;
    private final OperatorType operator;

    public UnaryExpression(int line, int column, Expression expression, OperatorType operator) {
        super(line, column);

        Objects.requireNonNull(expression, "Expressão não pode ser nula.");
        Objects.requireNonNull(operator, "Operador não pode ser nulo.");

        this.expression = expression;
        this.operator = operator;
    }

    // Construtor para testes
    public UnaryExpression(Expression expression, OperatorType operator) {
        this(1, 1, expression, operator);
    }

    public Expression getExpression() {
        return expression;
    }

    public OperatorType getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(operator).append(" ");
        appendExpression(sb, expression);
        return sb.toString();
    }

    private void appendExpression(StringBuilder sb, Expression expr) {
        if (needsParentheses(expr)) {
            sb.append("(").append(expr.toString()).append(")");
        } else {
            sb.append(expr.toString());
        }
    }

    public boolean needsParentheses(Expression expression) {
        return expression instanceof BinaryExpression;
    }

}
