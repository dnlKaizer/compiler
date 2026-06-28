package br.com.comcet.frontend.ast.declaration;

import br.com.comcet.frontend.ast.AstNode;

public abstract class Declaration extends AstNode {
    
    protected Declaration(int line, int column) {
        super(line, column);
    }

}
