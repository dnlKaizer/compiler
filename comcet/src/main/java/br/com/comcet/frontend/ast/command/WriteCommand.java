package br.com.comcet.frontend.ast.command;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import br.com.comcet.frontend.ast.NodeList;
import br.com.comcet.frontend.ast.expression.Expression;

public class WriteCommand extends Command {
    private final NodeList<Expression> expressions;
    private final boolean isNewLine;

    public WriteCommand(int line, int column, NodeList<Expression> expressions, boolean isNewLine) {
        super(line, column);
        Objects.requireNonNull(expressions, "Expressões não podem ser nulas.");
        this.expressions = expressions;
        this.isNewLine = isNewLine;
    }

    public WriteCommand(int line, int column, Expression expression, boolean isNewLine) {
        super(line, column);
        Objects.requireNonNull(expression, "Expressão não pode ser nula.");
        this.expressions = new NodeList<Expression>(line, column, Arrays.asList(expression));
        this.isNewLine = isNewLine;
    }

    // Construtor para testes
    public WriteCommand(Expression expression, boolean isNewLine) {
        this(1, 1, expression, isNewLine);
    }

    public List<Expression> getArguments() {
        return expressions.getItems();
    }

    public boolean isNewLine() {
        return isNewLine;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitWriteCommand(this);
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;
        List<Expression> exprList = expressions.getItems();
        
        StringBuilder sb = new StringBuilder();
        sb.append("write").append(isNewLine ? "ln(" : "(");
        for (int i = 0; i < exprList.size(); i++) {
            sb.append(exprList.get(i).toString());
            if (i < exprList.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append(");").append(br);
        
        return sb.toString();
    }

}
