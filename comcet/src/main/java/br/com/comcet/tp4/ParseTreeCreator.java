package br.com.comcet.tp4;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class ParseTreeCreator {
    public static ParseTree createTree(String content) {
        // 1. Ler o arquivo fonte
        CharStream input = CharStreams.fromString(content);

        // 2. Lexer: converte caracteres em tokens
        MiniPascalLexer lexer = new MiniPascalLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 3. Parser: converte tokens na ParseTree
        MiniPascalParser parser = new MiniPascalParser(tokens);

        // 4. Iniciar a análise a partir da regra inicial 'program'
        return parser.program();
    }

    public static ParseTree createTree(CharStream input) {
        // 1. Lexer: converte caracteres em tokens
        MiniPascalLexer lexer = new MiniPascalLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // 2. Parser: converte tokens na ParseTree
        MiniPascalParser parser = new MiniPascalParser(tokens);

        // 3. Iniciar a análise a partir da regra inicial 'program'
        return parser.program();
    }
}
