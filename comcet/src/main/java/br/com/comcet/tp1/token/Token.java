package br.com.comcet.tp1.token;

public record Token(
    TokenType type,
    String text,
    int line,
    int column
) {

    @Override
    public String toString() {
        return String.format("%s('%s') na linha %d e coluna %d.", type, text, line, column);
    }
}
