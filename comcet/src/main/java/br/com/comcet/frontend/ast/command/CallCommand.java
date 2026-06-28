package br.com.comcet.frontend.ast.command;

import java.util.Objects;

import br.com.comcet.frontend.ast.argument.Arguments;
import br.com.comcet.frontend.ast.expression.Identifier;
import br.com.comcet.frontend.ast.interfaces.IFunctionCall;

public class CallCommand extends Command implements IFunctionCall {
    private final Identifier identifier;
    private final Arguments arguments;

    public CallCommand(int line, int column, Identifier identifier, Arguments arguments) {
        super(line, column);
        Objects.requireNonNull(identifier, "Identificador do comando de chamada não pode ser nulo.");
        Objects.requireNonNull(arguments, "Argumentos do comando de chamada não podem ser nulos.");
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
