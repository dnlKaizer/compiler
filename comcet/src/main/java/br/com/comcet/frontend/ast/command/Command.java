package br.com.comcet.frontend.ast.command;

import br.com.comcet.frontend.ast.AstNode;

public abstract class Command extends AstNode {
    
    protected Command(int line, int column) {
        super(line, column);
    }

}
