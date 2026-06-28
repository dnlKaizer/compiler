package br.com.comcet.frontend.ast.declaration;

import java.util.Objects;

import br.com.comcet.frontend.ast.command.BlockCommand;
import br.com.comcet.frontend.ast.command.IndentationUtil;
import br.com.comcet.frontend.ast.expression.Identifier;
import br.com.comcet.frontend.type.VariableType;

public class SubroutineDeclaration extends Declaration {
    private final Identifier identifier;
    private final ParametersDeclaration parameters;
    private final VariableType returnType;
    private final VariableDeclarations localVariables;
    private final BlockCommand body;

    private final boolean isFunction;

    public SubroutineDeclaration(int line, int column, Identifier identifier, ParametersDeclaration parameters, VariableType returnType, VariableDeclarations localVariables, BlockCommand body) {
        super(line, column);

        Objects.requireNonNull(identifier, "Identificador não pode ser nulo.");
        Objects.requireNonNull(parameters, "Parâmetros não podem ser nulos.");
        Objects.requireNonNull(returnType, "Tipo de retorno não pode ser nulo.");
        Objects.requireNonNull(localVariables, "Variáveis locais não podem ser nulas.");
        Objects.requireNonNull(body, "Corpo da subrotina não pode ser nulo.");

        this.identifier = identifier;
        this.parameters = parameters;
        this.returnType = returnType;
        this.localVariables = localVariables;
        this.body = body;
        this.isFunction = returnType != VariableType.VOID;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public ParametersDeclaration getParameters() {
        return parameters;
    }

    public VariableType getReturnType() {
        return returnType;
    }

    public VariableDeclarations getLocalVariables() {
        return localVariables;
    }

    public BlockCommand getBody() {
        return body;
    }

    public boolean isFunction() {
        return isFunction;
    }

	@Override
	public String toString() {
        String br = IndentationUtil.NEWLINE;
		StringBuilder sb = new StringBuilder();
        sb.append(isFunction ? "function " : "procedure ").append(identifier.toString());
        sb.append("(").append(parameters.toString()).append(")");
        if (isFunction) sb.append(": ").append(returnType.toString());
        sb.append(";").append(br);
        sb.append(localVariables.toString());
        sb.append(body.toString());
        return sb.toString();
	}

}
