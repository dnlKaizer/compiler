package br.com.comcet.frontend.ast.expression;

public class LiteralBoolean extends Literal<Boolean> {

    public LiteralBoolean(int line, int column, Boolean value) {
        super(line, column, value);
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitLiteralBoolean(this);
    }

}
