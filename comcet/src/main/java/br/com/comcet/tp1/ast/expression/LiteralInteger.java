package br.com.comcet.tp1.ast.expression;

import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp1.token.Token;

public class LiteralInteger extends Literal<Integer> {
    public LiteralInteger(int line, int column, Integer value) {
        super(line, column, value);
        setType(VariableType.INTEGER);
    }

    public LiteralInteger(Integer value) {
        this(1, 1, value);
    }

    public static LiteralInteger of(Token token) {
        return new LiteralInteger(token.line(), token.column(), Integer.parseInt(token.text()));
    }
    
}
