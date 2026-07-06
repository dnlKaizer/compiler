package br.com.comcet.frontend.ast.expression;

public class LiteralString extends Literal<String> {

    public LiteralString(int line, int column, String value) {
        super(line, column, value);
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitLiteralString(this);
    }

    @Override
    public String toString() {
        String text = getValue().replace("\\", "\\\\")
                                .replace("\n", "\\n")
                                .replace("\t", "\\t")
                                .replace("\r", "\\r")
                                .replace("\'", "\\\'");
        return "\'" + text + "\'";
    }
    
}
