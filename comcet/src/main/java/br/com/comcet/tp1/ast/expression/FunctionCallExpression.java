package br.com.comcet.tp1.ast.expression;

import br.com.comcet.tp1.ast.argument.Arguments;

public class FunctionCallExpression extends Expression {
    private final Identifier identifier;
    private final Arguments arguments;

    public FunctionCallExpression(int line, int column, Identifier identifier, Arguments arguments) {
        super(line, column);
        this.identifier = identifier;
        this.arguments = arguments;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Arguments getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(identifier.toString()).append("(");
        for (int i = 0; i < arguments.getArguments().size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(arguments.getArguments().get(i).toString());
        }
        sb.append(")");
        return sb.toString();
    }

}
