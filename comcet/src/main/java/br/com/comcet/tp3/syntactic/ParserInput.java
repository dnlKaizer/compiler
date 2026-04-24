package br.com.comcet.tp3.syntactic;

import java.util.List;

import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp1.symbol.Symbol;
import br.com.comcet.tp1.symbol.SymbolTable;
import br.com.comcet.tp1.token.Token;
import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp2.scanner.Lexer;
import br.com.comcet.tp3.exception.ExceptionQueue;
import br.com.comcet.tp3.exception.SintaxException;

public class ParserInput {
    private final Lexer lexer;
    private final SymbolTable symbolTable;

    private Token currentToken;

    public ParserInput(Lexer lexer) {
        this.lexer = lexer;
        this.symbolTable = new SymbolTable();
    }

    public Token getCurrentToken() {
        return currentToken;
    }

    public TokenType getCurrentTokenType() {
        return currentToken.type();
    }

    public int getCurrentLine() {
        return currentToken.line();
    }

    public int getCurrentColumn() {
        return currentToken.column();
    }

    public boolean is(TokenType type) {
        return currentToken.type() == type;
    }

    public void advance() {
        currentToken = lexer.nextToken();
    }

    public void match(TokenType expectedType) {
        if (!is(expectedType)) {
            throw new SintaxException(
                    String.format("Esperado '%s', mas encontrado '%s'.", expectedType, currentToken.type()),
                    currentToken.line(),
                    currentToken.column());
        }
    }

    public void matchAndAdvance(TokenType expectedType) {
        try {
            match(expectedType);
            advance();
        } catch (SintaxException e) {
            ExceptionQueue.add(e);
        }
    }

    public void skipUntil(List<TokenType> types) {
        while (true) {
            if (types.contains(currentToken.type()) || is(TokenType.EOF)) {
                advance();
                break;
            }
            advance();
        }
    }

    public Identifier createIdentifier(Token token, VariableType type) {
        Symbol symbol = new Symbol(token.text(), type);
        symbolTable.addSymbol(symbol);
        return new Identifier(token, symbol);
    }

    public Identifier getIdentifier(Token token) {
        Symbol symbol = symbolTable.getSymbol(token.text());
        return new Identifier(token, symbol);
    }

}
