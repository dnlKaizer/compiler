package br.com.comcet.frontend.ast.command;

import br.com.comcet.frontend.ast.expression.Identifier;

public class ReadCommand extends Command {
    private final Identifier identifier;
    private final boolean isNewLine;

    public ReadCommand(int line, int column, Identifier identifier, boolean isNewLine) {
        super(line, column);
        this.identifier = identifier;
        this.isNewLine = isNewLine;
    }

    // Construtor para testes
    public ReadCommand(Identifier identifier, boolean isNewLine) {
        this(1, 1, identifier, isNewLine);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public boolean isNewLine() {
        return isNewLine;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitReadCommand(this);
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;
        StringBuilder sb = new StringBuilder();
        sb.append("read").append(isNewLine ? "ln(" : "(").append(identifier.getName()).append(");").append(br);
        return sb.toString();
    }
    
}
