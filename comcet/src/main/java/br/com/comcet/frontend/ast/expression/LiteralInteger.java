package br.com.comcet.frontend.ast.expression;

public class LiteralInteger extends Literal<Integer> {
    
    public LiteralInteger(int line, int column, Integer value) {
        super(line, column, value);
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitLiteralInteger(this);
    }
    
}
