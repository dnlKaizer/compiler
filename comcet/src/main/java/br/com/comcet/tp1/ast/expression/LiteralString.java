package br.com.comcet.tp1.ast.expression;

import br.com.comcet.tp1.token.Token;

public class LiteralString extends Literal<String> {
    public LiteralString(int line, int column, String value) {
        super(line, column, value);
    }

    public LiteralString(String value) {
        super(value);
    }

    public static LiteralString of(Token token) {
        String text = token.text();
        String unquoted = text.substring(1, text.length() - 1);
        return new LiteralString(token.line(), token.column(), unquoted);
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
