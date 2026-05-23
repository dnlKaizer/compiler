package br.com.comcet.tp4;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class ANTLRTestSetup {

    public static MiniPascalParser getParser(String content) {
        MiniPascalLexer lexer = new MiniPascalLexer(CharStreams.fromString(content));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return new MiniPascalParser(tokens);
    }

    public static MiniPascalParser.ExpressionContext parseExpression(String content) {
        return getParser(content).expression();
    }

    public static MiniPascalParser.CommandContext parseCommand(String content) {
        return getParser(content).command();
    }
}
