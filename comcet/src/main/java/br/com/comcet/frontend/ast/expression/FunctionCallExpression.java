package br.com.comcet.frontend.ast.expression;

import java.util.List;

import br.com.comcet.frontend.ast.NodeList;
import br.com.comcet.frontend.ast.interfaces.IFunctionCall;

public class FunctionCallExpression extends Expression implements IFunctionCall {
    private final Identifier identifier;
    private final NodeList<Expression> arguments;

    public FunctionCallExpression(int line, int column, Identifier identifier, NodeList<Expression> arguments) {
        super(line, column);
        this.identifier = identifier;
        this.arguments = arguments;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public List<Expression> getArguments() {
        return arguments.getItems();
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitFunctionCallExpression(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(identifier.toString()).append("(");
        for (int i = 0; i < arguments.getItems().size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(arguments.getItems().get(i).toString());
        }
        sb.append(")");
        return sb.toString();
    }

}
