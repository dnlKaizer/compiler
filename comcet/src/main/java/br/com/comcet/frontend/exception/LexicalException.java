package br.com.comcet.frontend.exception;

public class LexicalException extends CompilerException {

    public LexicalException(String message, int line, int column) {
        super(message, line, column);
    }
    
}
