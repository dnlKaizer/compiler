package br.com.comcet.tp1.ast.expression;

import br.com.comcet.tp1.ast.AstNode;

public abstract class Expression extends AstNode {

    protected Expression(int line, int column) {
        super(line, column);
    }

}
