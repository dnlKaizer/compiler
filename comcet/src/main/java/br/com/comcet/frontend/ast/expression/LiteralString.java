package br.com.comcet.frontend.ast.expression;

import br.com.comcet.frontend.type.VariableType;

public class LiteralString extends Literal<String> {

    public LiteralString(int line, int column, String value) {
        super(line, column, value);
        setType(VariableType.STRING);
    }

    @Override
    public String toString() {
        String text = getValue().replace("\\", "\\\\")
                                .replace("\n", "\\n")
                                .replace("\t", "\\t")
                                .replace("\r", "\\r")
                                .replace("\'", "\\\'");
        return "\'" + text + "\'";
    }
    
}
