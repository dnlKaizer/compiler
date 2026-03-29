package br.com.comcet.tp2.exception;

public class LexicalException extends RuntimeException {

    public LexicalException(String message, int line, int column) {
        super(String.format("%s (linha: %d, coluna: %d)", message, line, column));
    }
    
}
