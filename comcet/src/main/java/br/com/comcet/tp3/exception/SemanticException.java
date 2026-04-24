package br.com.comcet.tp3.exception;

public class SemanticException extends CompilerException {

    public SemanticException(String message, int line, int column) {
        super(message, line, column);
    }
    
}
