package br.com.comcet.tp2.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.comcet.tp1.token.Token;
import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp2.exception.LexicalException;
import br.com.comcet.tp2.scanner.charstream.StringCharStream;

public class LexerTest {

    @Test
    void testDeclaration() {
        String code = "var x, y : string;";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.VAR, t1.type());
        assertEquals("var", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.ID, t2.type());
        assertEquals("x", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.COMMA, t3.type());
        assertEquals(",", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.ID, t4.type());
        assertEquals("y", t4.text());

        Token t5 = scanner.nextToken();
        assertEquals(TokenType.COLON, t5.type());
        assertEquals(":", t5.text());

        Token t6 = scanner.nextToken();
        assertEquals(TokenType.STRING_TYPE, t6.type());
        assertEquals("string", t6.text());

        Token t7 = scanner.nextToken();
        assertEquals(TokenType.SEMICOLON, t7.type());
        assertEquals(";", t7.text());

        Token eof = scanner.nextToken();
        assertEquals(TokenType.EOF, eof.type());
    }

    @Test
    void testOperators() {
        String code = "> < >= <= := + - * /";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.GREATER_THAN, t1.type());
        assertEquals(">", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.LESS_THAN, t2.type());
        assertEquals("<", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.GREATER_EQUAL, t3.type());
        assertEquals(">=", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.LESS_EQUAL, t4.type());
        assertEquals("<=", t4.text());

        Token t5 = scanner.nextToken();
        assertEquals(TokenType.ASSIGN, t5.type());
        assertEquals(":=", t5.text());

        Token t6 = scanner.nextToken();
        assertEquals(TokenType.SUM, t6.type());
        assertEquals("+", t6.text());

        Token t7 = scanner.nextToken();
        assertEquals(TokenType.SUB, t7.type());
        assertEquals("-", t7.text());

        Token t8 = scanner.nextToken();
        assertEquals(TokenType.MULT, t8.type());
        assertEquals("*", t8.text());

        Token t9 = scanner.nextToken();
        assertEquals(TokenType.DIV, t9.type());
        assertEquals("/", t9.text());
    }

    @Test 
    void testKeywords() {
        String code = "if then else while do begin end";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.IF, t1.type());
        assertEquals("if", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.THEN, t2.type());
        assertEquals("then", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.ELSE, t3.type());
        assertEquals("else", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.WHILE, t4.type());
        assertEquals("while", t4.text());

        Token t5 = scanner.nextToken();
        assertEquals(TokenType.DO, t5.type());
        assertEquals("do", t5.text());

        Token t6 = scanner.nextToken();
        assertEquals(TokenType.BEGIN, t6.type());
        assertEquals("begin", t6.text());

        Token t7 = scanner.nextToken();
        assertEquals(TokenType.END, t7.type());
        assertEquals("end", t7.text());

        Token eof = scanner.nextToken();
        assertEquals(TokenType.EOF, eof.type());
    }

    @Test
    void testIfStatement() {
        String code = "if x >= 10 then y := 'big';";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.IF, t1.type());
        assertEquals("if", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.ID, t2.type());
        assertEquals("x", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.GREATER_EQUAL, t3.type());
        assertEquals(">=", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.INTEGER_LITERAL, t4.type());
        assertEquals("10", t4.text());

        Token t5 = scanner.nextToken();
        assertEquals(TokenType.THEN, t5.type());
        assertEquals("then", t5.text());

        Token t6 = scanner.nextToken();
        assertEquals(TokenType.ID, t6.type());
        assertEquals("y", t6.text());

        Token t7 = scanner.nextToken();
        assertEquals(TokenType.ASSIGN, t7.type());
        assertEquals(":=", t7.text());

        Token t8 = scanner.nextToken();
        assertEquals(TokenType.STRING_LITERAL, t8.type());
        assertEquals("big", t8.text());

        Token t9 = scanner.nextToken();
        assertEquals(TokenType.SEMICOLON, t9.type());
        assertEquals(";", t9.text());

        Token eof = scanner.nextToken();
        assertEquals(TokenType.EOF, eof.type());
    }

    @Test
    void testWhileStatement() {
        String code = "while x < 10 do begin x := x + 1; x := 5; end";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.WHILE, t1.type());
        assertEquals("while", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.ID, t2.type());
        assertEquals("x", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.LESS_THAN, t3.type());
        assertEquals("<", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.INTEGER_LITERAL, t4.type());
        assertEquals("10", t4.text());

        Token t5 = scanner.nextToken();
        assertEquals(TokenType.DO, t5.type());
        assertEquals("do", t5.text());

        Token t6 = scanner.nextToken();
        assertEquals(TokenType.BEGIN, t6.type());
        assertEquals("begin", t6.text());

        Token t7 = scanner.nextToken();
        assertEquals(TokenType.ID, t7.type());
        assertEquals("x", t7.text());

        Token t8 = scanner.nextToken();
        assertEquals(TokenType.ASSIGN, t8.type());
        assertEquals(":=", t8.text());

        Token t9 = scanner.nextToken();
        assertEquals(TokenType.ID, t9.type());
        assertEquals("x", t9.text());

        Token t10 = scanner.nextToken();
        assertEquals(TokenType.SUM, t10.type());
        assertEquals("+", t10.text());

        Token t11 = scanner.nextToken();
        assertEquals(TokenType.INTEGER_LITERAL, t11.type());
        assertEquals("1", t11.text());

        Token t12 = scanner.nextToken();
        assertEquals(TokenType.SEMICOLON, t12.type());
        assertEquals(";", t12.text());

        Token t13 = scanner.nextToken();
        assertEquals(TokenType.ID, t13.type());
        assertEquals("x", t13.text());

        Token t14 = scanner.nextToken();
        assertEquals(TokenType.ASSIGN, t14.type());
        assertEquals(":=", t14.text());

        Token t15 = scanner.nextToken();
        assertEquals(TokenType.INTEGER_LITERAL, t15.type());
        assertEquals("5", t15.text());

        Token t16 = scanner.nextToken();
        assertEquals(TokenType.SEMICOLON, t16.type());
        assertEquals(";", t16.text());

        Token t17 = scanner.nextToken();
        assertEquals(TokenType.END, t17.type());
        assertEquals("end", t17.text());
        
        Token eof = scanner.nextToken();
        assertEquals(TokenType.EOF, eof.type());
    }

    @Test
    void testAssignmentWithExpression() {
        String code = "x := 10 + 5;";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.ID, t1.type());
        assertEquals("x", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.ASSIGN, t2.type());
        assertEquals(":=", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.INTEGER_LITERAL, t3.type());
        assertEquals("10", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.SUM, t4.type());
        assertEquals("+", t4.text());

        Token t5 = scanner.nextToken();
        assertEquals(TokenType.INTEGER_LITERAL, t5.type());
        assertEquals("5", t5.text());

        Token t6 = scanner.nextToken();
        assertEquals(TokenType.SEMICOLON, t6.type());
        assertEquals(";", t6.text());

        Token eof = scanner.nextToken();
        assertEquals(TokenType.EOF, eof.type());
    }

    @Test
    void testStringLiteralWithEscape() {
        String code = "a := 'Hello, \\'World\\'!';";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.ID, t1.type());
        assertEquals("a", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.ASSIGN, t2.type());
        assertEquals(":=", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.STRING_LITERAL, t3.type());
        assertEquals("Hello, \'World\'!", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.SEMICOLON, t4.type());
        assertEquals(";", t4.text());

        Token eof = scanner.nextToken();
        assertEquals(TokenType.EOF, eof.type());
    }

    @Test
    void testUnclosedStringLiteral() {
        String code = "a := 'Hello, World!;";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.ID, t1.type());
        assertEquals("a", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.ASSIGN, t2.type());
        assertEquals(":=", t2.text());

        try {
            scanner.nextToken();
        } catch (LexicalException e) {
            assertEquals("String não fechada (linha: 1, coluna: 6)", e.getMessage());
            return;
        }
        throw new AssertionError("Expected LexicalException for unclosed string literal");
    }

    @Test
    void testUnclosedBlockComment() {
        String code = "{ Esse é um comentário de bloco não fechado ";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        try {
            scanner.nextToken();
        } catch (LexicalException e) {
            assertEquals("Comentário de bloco não fechado (linha: 1, coluna: 1)", e.getMessage());
            return;
        }
        throw new AssertionError("Expected LexicalException for unclosed block comment");
    }

    @Test
    void testLineComment() {
        String code = "x := 10; // Este é um comentário de linha\n y := 20;";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.ID, t1.type());
        assertEquals("x", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.ASSIGN, t2.type());
        assertEquals(":=", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.INTEGER_LITERAL, t3.type());
        assertEquals("10", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.SEMICOLON, t4.type());
        assertEquals(";", t4.text());

        Token t5 = scanner.nextToken();
        assertEquals(TokenType.ID, t5.type());
        assertEquals("y", t5.text());
    }

    @Test
    void testBlockComment() {
        String code = "x := 10; { Este é um comentário de bloco }\n y := 20;";
        Lexer scanner = Lexer.pascal(new StringCharStream(code));

        Token t1 = scanner.nextToken();
        assertEquals(TokenType.ID, t1.type());
        assertEquals("x", t1.text());

        Token t2 = scanner.nextToken();
        assertEquals(TokenType.ASSIGN, t2.type());
        assertEquals(":=", t2.text());

        Token t3 = scanner.nextToken();
        assertEquals(TokenType.INTEGER_LITERAL, t3.type());
        assertEquals("10", t3.text());

        Token t4 = scanner.nextToken();
        assertEquals(TokenType.SEMICOLON, t4.type());
        assertEquals(";", t4.text());

        Token t5 = scanner.nextToken();
        assertEquals(TokenType.ID, t5.type());
        assertEquals("y", t5.text());
    }

}
