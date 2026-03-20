package br.com.comcet.tp1.ast.command;

import br.com.comcet.tp1.ast.AstNode;

public abstract class Command extends AstNode {
    
    protected Command(int line, int column) {
        super(line, column);
    }

}
