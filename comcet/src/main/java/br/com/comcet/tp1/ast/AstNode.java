package br.com.comcet.tp1.ast;

public abstract class AstNode {
    private final int line;
    private final int column;

    protected AstNode(int line, int column) {
        this.validate(line, column);
        this.line = line;
        this.column = column;
    }

    private void validate(int line, int column) {
        if (line < 1 || column < 1) {
            throw new IllegalArgumentException("Linha e Coluna devem ser maiores que 0.");
        }
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

}
