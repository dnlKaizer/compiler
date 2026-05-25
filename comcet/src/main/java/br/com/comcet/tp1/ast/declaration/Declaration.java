package br.com.comcet.tp1.ast.declaration;

import br.com.comcet.tp1.ast.AstNode;

public abstract class Declaration extends AstNode {
    
    protected Declaration(int line, int column) {
        super(line, column);
    }

}
