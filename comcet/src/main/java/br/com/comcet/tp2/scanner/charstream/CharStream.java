package br.com.comcet.tp2.scanner.charstream;

public interface CharStream {
    
    public char peek();
    public char next();
    public boolean hasNext();

    public int getLine();
    public int getColumn();

}
