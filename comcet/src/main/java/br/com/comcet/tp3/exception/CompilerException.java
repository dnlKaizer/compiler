package br.com.comcet.tp3.exception;

public class CompilerException extends RuntimeException {

    public CompilerException(String message, int line, int column) {
        super(String.format("%s (linha: %d, coluna: %d)", message, line, column));
    }
    
}
