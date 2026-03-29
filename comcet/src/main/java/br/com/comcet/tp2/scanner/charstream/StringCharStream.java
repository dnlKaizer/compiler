package br.com.comcet.tp2.scanner.charstream;

public class StringCharStream implements CharStream {

    private final String input;
    private final int length;

    private int position = 0;
    private int line = 1;
    private int column = 1;

    public StringCharStream(String input) {
        this.input = input;
        this.length = input.length();
    }

    @Override
    public char peek() {
        if (!hasNext()) return '\0';
        
        return input.charAt(position);
    }

    @Override
    public char next() {
        if (!hasNext()) return '\0';
        
        char actualCharacter = input.charAt(position++);
        
        if (actualCharacter == '\n') {
            line++;
            column = 1;
        } else {
            column++;
        }
        
        return actualCharacter;
    }

    @Override
    public boolean hasNext() {
        return position < length;
    }

    @Override
    public int getLine() { 
        return line; 
    }

    @Override
    public int getColumn() { 
        return column; 
    }

}
