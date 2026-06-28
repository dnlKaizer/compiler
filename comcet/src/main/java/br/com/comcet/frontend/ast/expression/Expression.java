package br.com.comcet.frontend.ast.expression;

import br.com.comcet.frontend.ast.AstNode;
import br.com.comcet.frontend.type.VariableType;

public abstract class Expression extends AstNode {
    private VariableType type;

    protected Expression(int line, int column) {
        super(line, column);
    }

    public VariableType setType(VariableType type) {
        this.type = type;
        return type;
    }

    public VariableType getType() {
        return type;
    }

}
