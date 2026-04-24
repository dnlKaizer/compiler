package br.com.comcet.tp3.syntactic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import br.com.comcet.tp1.ast.command.AssignmentCommand;
import br.com.comcet.tp1.ast.command.BlockCommand;
import br.com.comcet.tp1.ast.command.Command;
import br.com.comcet.tp1.ast.command.IfCommand;
import br.com.comcet.tp1.ast.command.RepeatCommand;
import br.com.comcet.tp1.ast.command.WhileCommand;
import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp3.exception.ExceptionQueue;
import br.com.comcet.tp3.exception.CompilerException;
import br.com.comcet.tp3.exception.SintaxException;
import br.com.comcet.tp3.semantic.SemanticValidation;

public class CommandParser {

    public static Command parseCommand(ParserInput input) {
        switch (input.getCurrentTokenType()) {
            case IF:
                return parseCommandWithHandler(List.of(TokenType.SEMICOLON, TokenType.END), input, (inp) -> parseIfCommand(inp));
            case WHILE:
                return parseCommandWithHandler(List.of(TokenType.SEMICOLON, TokenType.END), input, (inp) -> parseWhileCommand(inp));
            case REPEAT:
                return parseCommandWithHandler(List.of(TokenType.SEMICOLON, TokenType.END), input, (inp) -> parseRepeatCommand(inp));
            case ID:
                return parseCommandWithHandler(List.of(TokenType.SEMICOLON), input, (inp) -> parseAssignmentCommand(inp));
            case BEGIN:
                return parseCommandWithHandler(List.of(TokenType.END), input, (inp) -> parseBlockCommand(inp));
            case EOF:
                return null;
            default:
                SintaxException e = new SintaxException("Comando não reconhecido: " + input.getCurrentTokenType(),
                    input.getCurrentLine(), input.getCurrentColumn());
                return handleParserException(List.of(TokenType.IF, TokenType.WHILE, TokenType.REPEAT, TokenType.ID, TokenType.BEGIN), input, e);
        }
    }

    private static Command parseCommandWithHandler(List<TokenType> stopTokens, ParserInput input, Function<ParserInput, Command> commandSupplier) {
        try {
            return commandSupplier.apply(input);
        } catch (CompilerException e) {
            return handleParserException(stopTokens, input, e);
        }
    }

    private static Command handleParserException(List<TokenType> stopTokens, ParserInput input, CompilerException e) {
        input.skipUntil(stopTokens);
        ExceptionQueue.add(e);
        return parseCommand(input);
    }

    public static BlockCommand parseBlockCommand(ParserInput input) {
        int line = input.getCurrentLine();
        int column = input.getCurrentColumn();
        List<Command> commands = new ArrayList<>();

        input.matchAndAdvance(TokenType.BEGIN);

        while (input.getCurrentTokenType() != TokenType.END) {
            Command command = parseCommand(input);
            if (command == null) return null;
            commands.add(command);
        }

        input.matchAndAdvance(TokenType.END);

        return new BlockCommand(line, column, commands);
    }

    public static IfCommand parseIfCommand(ParserInput input) {
        int line = input.getCurrentLine();
        int column = input.getCurrentColumn();
        
        input.matchAndAdvance(TokenType.IF);
        input.matchAndAdvance(TokenType.LPAREN);

        Expression condition = ExpressionParser.parseExpression(input);
        SemanticValidation.validateConditionExpression(condition);

        input.matchAndAdvance(TokenType.RPAREN);
        input.matchAndAdvance(TokenType.THEN);

        Command thenCommand = parseCommand(input);
        if (thenCommand == null) return null;

        Command elseCommand = null;

        if (input.is(TokenType.ELSE)) {
            input.advance();
            elseCommand = parseCommand(input);
            if (elseCommand == null) return null;
        }

        return new IfCommand(line, column, condition, thenCommand, elseCommand);
    }

    public static WhileCommand parseWhileCommand(ParserInput input) {
        int line = input.getCurrentLine();
        int column = input.getCurrentColumn();

        input.matchAndAdvance(TokenType.WHILE);
        input.matchAndAdvance(TokenType.LPAREN);

        Expression condition = ExpressionParser.parseExpression(input);
        SemanticValidation.validateConditionExpression(condition);

        input.matchAndAdvance(TokenType.RPAREN);
        input.matchAndAdvance(TokenType.DO);

        Command body = parseCommand(input);
        if (body == null) return null;

        return new WhileCommand(line, column, condition, body);
    }

    public static RepeatCommand parseRepeatCommand(ParserInput input) {
        int line = input.getCurrentLine();
        int column = input.getCurrentColumn();

        input.matchAndAdvance(TokenType.REPEAT);

        Command body = parseCommand(input);
        if (body == null) return null;

        input.matchAndAdvance(TokenType.UNTIL);

        Expression condition = ExpressionParser.parseExpression(input);
        SemanticValidation.validateConditionExpression(condition);

        return new RepeatCommand(line, column, condition, body);
    }

    public static AssignmentCommand parseAssignmentCommand(ParserInput input) {
        input.match(TokenType.ID);
        Identifier id = input.getIdentifier(input.getCurrentToken());
        input.advance();
        
        input.matchAndAdvance(TokenType.ASSIGN);

        Expression expression = ExpressionParser.parseExpression(input);
        SemanticValidation.validateAssignmentCompatibility(id, expression);

        input.matchAndAdvance(TokenType.SEMICOLON);

        return new AssignmentCommand(id.getLine(), id.getColumn(), id, expression);
    }

}
