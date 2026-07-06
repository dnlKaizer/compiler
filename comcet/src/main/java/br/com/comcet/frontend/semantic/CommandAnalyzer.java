package br.com.comcet.frontend.semantic;

import br.com.comcet.frontend.ast.argument.Arguments;
import br.com.comcet.frontend.ast.command.*;
import br.com.comcet.frontend.ast.expression.Expression;
import br.com.comcet.frontend.ast.expression.Identifier;
import br.com.comcet.frontend.exception.SemanticException;
import br.com.comcet.frontend.semantic.symbol.ISymbol;
import br.com.comcet.frontend.semantic.symbol.VariableSymbol;
import br.com.comcet.frontend.type.VariableType;

public class CommandAnalyzer implements CommandVisitor {

    private final SemanticContext context;
    private final ExpressionAnalyzer expressionAnalyzer;

    public CommandAnalyzer(SemanticContext context, ExpressionAnalyzer expressionAnalyzer) {
        this.context = context;
        this.expressionAnalyzer = expressionAnalyzer;
    }

    @Override
    public void visitBlockCommand(BlockCommand command) {
        command.getCommands().forEach(cmmd -> cmmd.accept(this));
    }

    @Override
    public void visitAssignmentCommand(AssignmentCommand command) {
        Identifier id = command.getId();

        VariableSymbol varSymbol = visitVariableIdentifier(id);
        if (varSymbol == null)
            return;

        VariableType idType = varSymbol.getType();

        VariableType expressionType = command.getExpression().accept(expressionAnalyzer);
        if (expressionType == VariableType.ERROR)
            return;

        if (idType != expressionType) {
            String description = String.format("Tipo do identificador '%s' é '%s', mas expressão é do tipo '%s'",
                    id.getName(), idType, expressionType);
            context.addException(new SemanticException(description, command.getLine(), command.getColumn()));
        }

        varSymbol.initialize();
    }

    @Override
    public void visitCallCommand(CallCommand command) {
        int line = command.getLine();
        int column = command.getColumn();
        Identifier identifier = command.getIdentifier();
        Arguments arguments = command.getArguments();

        expressionAnalyzer.visitFunctionCall(identifier, arguments, line, column);
    }

    @Override
    public void visitIfCommand(IfCommand command) {
        verifyCondition(command.getCondition(), "if", command.getLine(), command.getColumn());

        command.getThenBranch().accept(this);

        if (command.getElseBranch() != null)
            command.getElseBranch().accept(this);
    }

    public void visitRepeatCommand(RepeatCommand command) {
        verifyCondition(command.getCondition(), "repeat", command.getLine(), command.getColumn());

        command.getBody().accept(this);
    }

    public void visitWhileCommand(WhileCommand command) {
        verifyCondition(command.getCondition(), "while", command.getLine(), command.getColumn());

        command.getBody().accept(this);
    }

    public void visitReadCommand(ReadCommand command) {
        Identifier identifier = command.getIdentifier();

        VariableSymbol varSymbol = visitVariableIdentifier(identifier);
        if (varSymbol == null)
            return;

        varSymbol.initialize();
    }

    public void visitWriteCommand(WriteCommand command) {
        command.getArguments().getExpressions().forEach(expression -> expression.accept(expressionAnalyzer));
    }

    private void verifyCondition(Expression condition, String commandName, int line, int column) {
        VariableType conditionType = condition.accept(expressionAnalyzer);
        if (conditionType == VariableType.ERROR)
            return;

        if (conditionType != VariableType.BOOLEAN) {
            String description = String.format("Tipo da condição do comando '%s' deve ser 'boolean', mas é '%s'",
                    commandName, conditionType);
            context.addException(new SemanticException(description, line, column));
        }
    }

    private VariableSymbol visitVariableIdentifier(Identifier identifier) {
        VariableType type = expressionAnalyzer.visitIdentifier(identifier);
        if (type == VariableType.ERROR)
            return null;

        ISymbol symbol = context.getSymbol(identifier.getName());
        if (!(symbol instanceof VariableSymbol)) {
            String description = String.format("Identificador '%s' não é uma variável", identifier.getName());
            context.addException(new SemanticException(description, identifier.getLine(), identifier.getColumn()));
            return null;
        }

        return (VariableSymbol) symbol;
    }

}
