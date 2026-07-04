package br.com.comcet.frontend.exception;

public class SyntaticException extends CompilerException {

    public SyntaticException(String message, int line, int column) {
        super(message, line, column);
    }
    
}
