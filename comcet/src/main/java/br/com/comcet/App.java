package br.com.comcet;

import br.com.comcet.tp0.TextStatistics;
import br.com.comcet.tp0.TextStatisticsCalculator;
import br.com.comcet.tp1.ast.AstCreator;
import br.com.comcet.tp1.token.Token;
import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp2.scanner.Lexer;
import br.com.comcet.tp2.scanner.charstream.StringCharStream;
import br.com.comcet.tp3.syntactic.Parser;
import br.com.comcet.utils.TextContentProvider;

public class App {
    public static void main(String[] args) {
        testTP03(args);
    }

    public static void testTP00(String[] args) {
        String content = TextContentProvider.getContent(args);
        if (content == null) return;
        TextStatistics textStatistics = TextStatisticsCalculator.calculate(content);
        System.out.println(textStatistics);
    }

    public static void testTP01(String[] args) {
        System.out.println(AstCreator.createTree());
    }

    public static void testTP02(String[] args) {
        String content = TextContentProvider.getContent(args);
        if (content == null) return;
        Lexer lexer = Lexer.pascal(new StringCharStream(content));
        Token token;
        do {
            token = lexer.nextToken();
            System.out.println(token);
        } while (token.type() != TokenType.EOF);
    }

    public static void testTP03(String[] args) {
        String content = TextContentProvider.getContent(args);
        if (content == null) return;
        Lexer lexer = Lexer.pascal(new StringCharStream(content));
        System.out.println(Parser.parseProgram(lexer));
    }

}