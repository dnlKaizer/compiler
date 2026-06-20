package br.com.comcet.tp1.ast.expression;

import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp1.token.Token;

public class LiteralBoolean extends Literal<Boolean> {
    public LiteralBoolean(int line, int column, Boolean value) {
        super(line, column, value);
        setType(VariableType.BOOLEAN);
    }

    public LiteralBoolean(Boolean value) {
        this(1, 1, value);
    }

    public static LiteralBoolean of(Token token) {
        return new LiteralBoolean(token.line(), token.column(), Boolean.parseBoolean(token.text()));
    }
    
}
