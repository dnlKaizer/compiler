package br.com.comcet.tp3.syntactic;

import java.util.ArrayList;
import java.util.List;

import br.com.comcet.tp1.ast.declaration.VariableDeclaration;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp1.token.Token;
import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp3.exception.ExceptionQueue;
import br.com.comcet.tp3.exception.CompilerException;
import br.com.comcet.tp3.exception.SintaxException;

public class VariableDeclarationParser {
    public static List<VariableDeclaration> parseVariableDeclarations(ParserInput input) {
        try {
            input.match(TokenType.VAR);
        } catch (CompilerException e) {
            ExceptionQueue.add(e);
        }
        input.advance();
        
        List<VariableDeclaration> declarations = new ArrayList<>();

        while (input.getCurrentTokenType() != TokenType.BEGIN) {
            try {
                declarations.add(parseVariableDeclaration(input));
            } catch (CompilerException e) {
                ExceptionQueue.add(e);
            }
        }

        return declarations;
    }

    public static VariableDeclaration parseVariableDeclaration(ParserInput input) {
        List<Token> identifiersTokens = new ArrayList<>();
        int line = input.getCurrentLine();
        int column = input.getCurrentColumn();

        while (true) {
            input.match(TokenType.ID);
            identifiersTokens.add(input.getCurrentToken());
            input.advance();

            if (input.getCurrentTokenType() == TokenType.COLON) {
                input.advance();
                break;
            }

            if (input.getCurrentTokenType() == TokenType.COMMA) {
                input.advance();
                continue;
            }

            throw new SintaxException("Esperado ':' ou ',' em declaração de variável. Recebido: " + input.getCurrentTokenType(),
                    input.getCurrentLine(), input.getCurrentColumn());
        }

        if (!input.getCurrentTokenType().isType()) {
            throw new SintaxException("Tipo de dado esperado em declaração de variável. Recebido: " + input.getCurrentTokenType(),
                    input.getCurrentLine(), input.getCurrentColumn());
        }

        VariableType type = VariableType.fromTokenType(input.getCurrentToken());
        input.advance();

        input.matchAndAdvance(TokenType.SEMICOLON);

        List<Identifier> identifiers = new ArrayList<>();
        identifiersTokens.forEach(token -> {
            identifiers.add(input.createIdentifier(token, type));
        });

        return new VariableDeclaration(line, column, identifiers, type);
    }
}
