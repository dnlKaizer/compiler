package br.com.comcet.tp2.scanner;

import br.com.comcet.tp1.scanner.IScanner;
import br.com.comcet.tp1.token.Token;
import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp2.exception.LexicalException;
import br.com.comcet.tp2.scanner.charstream.CharStream;
import br.com.comcet.tp2.scanner.identifier.KeywordIdentifier;
import br.com.comcet.tp2.scanner.identifier.SymbolIdentifier;
import br.com.comcet.tp2.scanner.lexical.LexicalRules;
import br.com.comcet.tp2.scanner.lexical.PascalLexicalRules;

public class Lexer implements IScanner {

    private final CharStream charStream;
    private final LexicalRules lexicalRules;

    public Lexer(CharStream charStream, LexicalRules lexicalRules) {
        this.charStream = charStream;
        this.lexicalRules = lexicalRules;
    }

    public static Lexer pascal(CharStream charStream) {
        return new Lexer(charStream, new PascalLexicalRules());
    }

    @Override
    public Token nextToken() {
        while (true) {
            this.skipWhitespaces();

            int line = charStream.getLine();
            int column = charStream.getColumn();
            char lookahead = charStream.peek();
            
            if (lexicalRules.isEndOfFile(lookahead)) {
                return new Token(TokenType.EOF, "", line, column);
            }
            
            char currentChar = charStream.next();
        
            if (lexicalRules.isSingleLineCommentStart(currentChar, charStream.peek())) {
                skipSingleLineComment();
                continue;
            }
            if (lexicalRules.isBlockCommentStart(currentChar)) {
                skipBlockComment(line, column);
                continue;
            } 

            return processToken(currentChar, line, column);
        }
    }

    private Token processToken(char currentChar, int line, int column) {
        if (lexicalRules.isIdentifierStart(currentChar)) {
            return readWord(currentChar, line, column);
        }
        if (lexicalRules.isDigit(currentChar)) {
            return readDigit(currentChar, line, column);
        }
        if (lexicalRules.isStringDelimiter(currentChar)) {
            return readString(line, column);
        }

        return readSymbol(currentChar, line, column);
    }

    private void skipWhitespaces() {
        while (lexicalRules.isWhitespace(charStream.peek())) {
            charStream.next();
        }
    }

    private void skipSingleLineComment() {
        while (!lexicalRules.isLineBreak(charStream.peek()) && !lexicalRules.isEndOfFile(charStream.peek())) {
            charStream.next();
        }
    }

    private void skipBlockComment(int line, int column) {
        while (!lexicalRules.isBlockCommentEnd(charStream.peek())) {
            if (lexicalRules.isEndOfFile(charStream.peek())) {
                throw new LexicalException("Comentário de bloco não fechado", line, column);
            }
            charStream.next();
        }
        charStream.next();
    }

    private Token readWord(char firstChar, int line, int column) {
        StringBuilder tokenText = new StringBuilder().append(firstChar);

        while (lexicalRules.isIdentifierPart(charStream.peek())) {
            tokenText.append(charStream.next());
        }
        
        if (KeywordIdentifier.isKeyword(tokenText.toString())) {
            return new Token(KeywordIdentifier.identify(tokenText.toString()), tokenText.toString(), line, column);
        }

        return new Token(TokenType.ID, tokenText.toString(), line, column);
    }

    private Token readDigit(char firstChar, int line, int column) {
        StringBuilder tokenText = new StringBuilder().append(firstChar);

        while (lexicalRules.isDigit(charStream.peek())) {
            tokenText.append(charStream.next());
        }

        return new Token(TokenType.INTEGER_LITERAL, tokenText.toString(), line, column);
    }

    private Token readString(int line, int column) {
        StringBuilder tokenText = new StringBuilder();

        while (!lexicalRules.isStringDelimiter(charStream.peek())) {
            if (lexicalRules.isEndOfFile(charStream.peek())) {
                throw new LexicalException("String não fechada", line, column);
            }

            char currentChar = charStream.next();

            if (lexicalRules.isEscapeChar(currentChar, charStream.peek())) {
                currentChar = lexicalRules.convertEscapeChar(currentChar, charStream.next());
            }

            tokenText.append(currentChar);
        }
        charStream.next();

        return new Token(TokenType.STRING_LITERAL, tokenText.toString(), line, column);
    }

    private Token readSymbol(char firstChar, int line, int column) {
        String singleOperator = String.valueOf(firstChar);
        String doubleOperator = singleOperator + charStream.peek();

        if (SymbolIdentifier.isSymbol(doubleOperator)) {
            charStream.next();
            return new Token(SymbolIdentifier.identify(doubleOperator), doubleOperator, line, column);
        }

        if (SymbolIdentifier.isSymbol(singleOperator)) {
            return new Token(SymbolIdentifier.identify(singleOperator), singleOperator, line, column);
        }

        throw new LexicalException("Caractere inválido", line, column);
    }

}
