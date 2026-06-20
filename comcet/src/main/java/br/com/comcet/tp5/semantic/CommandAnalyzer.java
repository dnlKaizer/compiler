package br.com.comcet.tp5.semantic;

import br.com.comcet.tp1.ast.argument.Arguments;
import br.com.comcet.tp1.ast.command.*;
import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp3.exception.SemanticException;
import br.com.comcet.tp5.symbol.ISymbol;
import br.com.comcet.tp5.symbol.VariableSymbol;

public class CommandAnalyzer {

    private final SemanticContext context;
    private final ExpressionAnalyzer expressionAnalyzer;

    public CommandAnalyzer(SemanticContext context, ExpressionAnalyzer expressionAnalyzer) {
        this.context = context;
        this.expressionAnalyzer = expressionAnalyzer;
    }

    public void visit(Command command) {
        if (command instanceof BlockCommand)
            visitBlockCommand((BlockCommand) command);
        else if (command instanceof AssignmentCommand)
            visitAssignmentCommand((AssignmentCommand) command);
        else if (command instanceof CallCommand)
            visitCallCommand((CallCommand) command);
        else if (command instanceof IfCommand)
            visitIfCommand((IfCommand) command);
        else if (command instanceof RepeatCommand)
            visitRepeatCommand((RepeatCommand) command);
        else if (command instanceof WhileCommand)
            visitWhileCommand((WhileCommand) command);
        else if (command instanceof ReadCommand)
            visitReadCommand((ReadCommand) command);
        else if (command instanceof WriteCommand)
            visitWriteCommand((WriteCommand) command);
        else
            throw new IllegalArgumentException("Tipo de comando não suportado: " + command.getClass().getSimpleName());
    }

    public void visitBlockCommand(BlockCommand command) {
        command.getCommands().forEach(this::visit);
    }

    public void visitAssignmentCommand(AssignmentCommand command) {
        Identifier id = command.getId();

        VariableSymbol varSymbol = visitVariableIdentifier(id);
        if (varSymbol == null)
            return;

        VariableType idType = varSymbol.getType();

        VariableType expressionType = expressionAnalyzer.visit(command.getExpression());
        if (expressionType == VariableType.ERROR)
            return;

        if (idType != expressionType) {
            String description = String.format("Tipo do identificador '%s' é '%s', mas expressão é do tipo '%s'",
                    id.getName(), idType, expressionType);
            context.addException(new SemanticException(description, command.getLine(), command.getColumn()));
        }

        varSymbol.initialize();
    }

    public void visitCallCommand(CallCommand command) {
        int line = command.getLine();
        int column = command.getColumn();
        Identifier identifier = command.getIdentifier();
        Arguments arguments = command.getArguments();

        expressionAnalyzer.visitFunctionCall(identifier, arguments, line, column);
    }

    public void visitIfCommand(IfCommand command) {
        verifyCondition(command.getCondition(), "if", command.getLine(), command.getColumn());

        visit(command.getThenBranch());
        if (command.getElseBranch() != null)
            visit(command.getElseBranch());
    }

    public void visitRepeatCommand(RepeatCommand command) {
        verifyCondition(command.getCondition(), "repeat", command.getLine(), command.getColumn());

        visit(command.getBody());
    }

    public void visitWhileCommand(WhileCommand command) {
        verifyCondition(command.getCondition(), "while", command.getLine(), command.getColumn());

        visit(command.getBody());
    }

    public void visitReadCommand(ReadCommand command) {
        Identifier identifier = command.getIdentifier();

        VariableSymbol varSymbol = visitVariableIdentifier(identifier);
        if (varSymbol == null)
            return;

        varSymbol.initialize();
    }

    public void visitWriteCommand(WriteCommand command) {
        command.getArguments().getExpressions().forEach(expressionAnalyzer::visit);
    }

    private void verifyCondition(Expression condition, String commandName, int line, int column) {
        VariableType conditionType = expressionAnalyzer.visit(condition);
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
