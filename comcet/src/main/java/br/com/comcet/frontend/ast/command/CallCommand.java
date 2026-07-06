package br.com.comcet.frontend.ast.command;

import java.util.List;
import java.util.Objects;

import br.com.comcet.frontend.ast.NodeList;
import br.com.comcet.frontend.ast.expression.Expression;
import br.com.comcet.frontend.ast.expression.Identifier;
import br.com.comcet.frontend.ast.interfaces.IFunctionCall;

public class CallCommand extends Command implements IFunctionCall {
    private final Identifier identifier;
    private final NodeList<Expression> arguments;

    public CallCommand(int line, int column, Identifier identifier, NodeList<Expression> arguments) {
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
    public List<Expression> getArguments() {
        return arguments.getItems();
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitCallCommand(this);
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
