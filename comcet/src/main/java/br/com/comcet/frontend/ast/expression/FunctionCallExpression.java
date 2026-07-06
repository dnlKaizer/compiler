package br.com.comcet.frontend.ast.expression;

import br.com.comcet.frontend.ast.argument.Arguments;
import br.com.comcet.frontend.ast.interfaces.IFunctionCall;

public class FunctionCallExpression extends Expression implements IFunctionCall {
    private final Identifier identifier;
    private final Arguments arguments;

    public FunctionCallExpression(int line, int column, Identifier identifier, Arguments arguments) {
        super(line, column);
        this.identifier = identifier;
        this.arguments = arguments;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public Arguments getArguments() {
        return arguments;
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitFunctionCallExpression(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(identifier.toString()).append("(");
        for (int i = 0; i < arguments.getExpressions().size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(arguments.getExpressions().get(i).toString());
        }
        sb.append(")");
        return sb.toString();
    }

}
