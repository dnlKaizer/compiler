package br.com.comcet.frontend.ast.expression;

import br.com.comcet.frontend.type.VariableType;

public class LiteralBoolean extends Literal<Boolean> {

    public LiteralBoolean(int line, int column, Boolean value) {
        super(line, column, value);
        setType(VariableType.BOOLEAN);
    }

}
