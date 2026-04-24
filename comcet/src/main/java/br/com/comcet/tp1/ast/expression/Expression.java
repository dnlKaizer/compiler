package br.com.comcet.tp1.ast.expression;

import java.util.Objects;

import br.com.comcet.tp1.ast.AstNode;
import br.com.comcet.tp1.ast.type.VariableType;

public abstract class Expression extends AstNode {

    private final VariableType returnType;

    protected Expression(int line, int column, VariableType returnType) {
        super(line, column);
        Objects.requireNonNull(returnType, "Tipo de retorno da expressão não pode ser nulo.");
        this.returnType = returnType;
    }

    public VariableType getReturnType() {
        return returnType;
    }

}
