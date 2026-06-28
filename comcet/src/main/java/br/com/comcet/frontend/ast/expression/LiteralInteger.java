package br.com.comcet.frontend.ast.expression;

import br.com.comcet.frontend.type.VariableType;

public class LiteralInteger extends Literal<Integer> {
    
    public LiteralInteger(int line, int column, Integer value) {
        super(line, column, value);
        setType(VariableType.INTEGER);
    }
    
}
