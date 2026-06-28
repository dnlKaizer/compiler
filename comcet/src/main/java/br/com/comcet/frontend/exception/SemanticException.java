package br.com.comcet.frontend.exception;

public class SemanticException extends CompilerException {

    public SemanticException(String message, int line, int column) {
        super(message, line, column);
    }
    
}
