package br.com.comcet.tp3.exception;

public class SintaxException extends CompilerException {

    public SintaxException(String message, int line, int column) {
        super(message, line, column);
    }
    
}
