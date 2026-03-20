package br.com.comcet.tp1.ast.expression;

import java.util.Objects;

public class BinaryExpression extends Expression {
    private final Expression left;
    private final Expression right;
    private final String operator;

    public BinaryExpression(int line, int column, Expression left, Expression right, String operator) {
        super(line, column);

        Objects.requireNonNull(left, "Expressão à esquerda não pode ser nula.");
        Objects.requireNonNull(right, "Expressão à direita não pode ser nula.");
        Objects.requireNonNull(operator, "Operador não pode ser nulo.");

        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    // Construtor para testes
    public BinaryExpression(Expression left, Expression right, String operator) {
        this(1, 1, left, right, operator);
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(left.toString()).append(" ").append(operator).append(" ").append(right.toString());
        return sb.toString();
    }

}
