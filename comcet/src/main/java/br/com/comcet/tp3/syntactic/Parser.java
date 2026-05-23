package br.com.comcet.tp3.syntactic;

import java.util.List;
import java.util.Stack;

import br.com.comcet.tp1.ast.command.Command;
import br.com.comcet.tp1.ast.declaration.VariableDeclaration;
import br.com.comcet.tp1.ast.declaration.VariableDeclarations;
import br.com.comcet.tp1.ast.program.Program;
import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp2.scanner.Lexer;
import br.com.comcet.tp3.exception.ExceptionQueue;
import br.com.comcet.tp3.exception.CompilerException;

public class Parser {

    private Parser() {}

    public static Program parseProgram(Lexer lexer) {
        ParserInput input = new ParserInput(lexer);
        String programName = "UnnamedProgram";
        
        try {
            input.advance();
            input.matchAndAdvance(TokenType.PROGRAM);
            input.match(TokenType.ID);
            programName = input.getCurrentToken().text();
            input.advance();
            input.matchAndAdvance(TokenType.SEMICOLON);
        } catch (CompilerException e) {
            ExceptionQueue.add(e);
        }

        int varsLine = input.getCurrentLine();
        int varsColumn = input.getCurrentColumn();

        List<VariableDeclaration> vars = VariableDeclarationParser.parseVariableDeclarations(input);
        VariableDeclarations variableDeclarations = new VariableDeclarations(varsLine, varsColumn, vars);
        Command command = CommandParser.parseCommand(input);

        input.match(TokenType.EOF);

        return new Program(1, 1, variableDeclarations, command, programName, createExceptionStack());
    }

    private static Stack<CompilerException> createExceptionStack() {
        Stack<CompilerException> stack = new Stack<>();
        while (true) {
            CompilerException exception = ExceptionQueue.poll();
            if (exception == null) {
                break;
            }
            stack.push(exception);
        }
        return stack;
    }

}
