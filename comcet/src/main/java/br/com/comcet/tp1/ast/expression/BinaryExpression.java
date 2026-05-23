package br.com.comcet.tp1.ast.expression;

import java.util.Objects;

import br.com.comcet.tp1.ast.type.OperatorType;

public class BinaryExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final OperatorType operator;

    public BinaryExpression(int line, int column, Expression left, Expression right, OperatorType operator) {
        super(line, column);

        Objects.requireNonNull(left, "Expressão à esquerda não pode ser nula.");
        Objects.requireNonNull(right, "Expressão à direita não pode ser nula.");
        Objects.requireNonNull(operator, "Operador não pode ser nulo.");

        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    // Construtor para testes
    public BinaryExpression(Expression left, Expression right, OperatorType operator) {
        this(1, 1, left, right, operator);
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public OperatorType getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendExpression(sb, left);
        sb.append(" ").append(operator).append(" ");
        appendExpression(sb, right);
        return sb.toString();
    }

    private void appendExpression(StringBuilder sb, Expression expr) {
        if (needsParentheses(expr)) {
            sb.append("(").append(expr.toString()).append(")");
        } else {
            sb.append(expr.toString());
        }
    }

    private boolean needsParentheses(Expression child) {
        if (!(child instanceof BinaryExpression)) {
            return false;
        }

        BinaryExpression childBinary = (BinaryExpression) child;
        return childBinary.operator.getPrecedence() > this.operator.getPrecedence();
    }

}
