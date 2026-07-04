package br.com.comcet;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import br.com.comcet.backend.bytecode.CodeGen;
import br.com.comcet.frontend.ast.program.Program;
import br.com.comcet.frontend.exception.AntlrErrorCollector;
import br.com.comcet.frontend.exception.SemanticExceptionQueue;
import br.com.comcet.frontend.parser.MiniPascalAntlrVisitor;
import br.com.comcet.frontend.parser.MiniPascalLexer;
import br.com.comcet.frontend.parser.MiniPascalParser;
import br.com.comcet.frontend.semantic.SemanticAnalyzer;

public class Compiler {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("ERRO: Nenhum arquivo de entrada fornecido.");
            return;
        }

        String caminhoArquivo = args[0];
        System.out.println();
        System.out.println("LOG: Iniciando compilação...");

        try {
            CharStream input = CharStreams.fromFileName(caminhoArquivo);

            System.out.println("LOG: Criando árvore de análise sintática...");

            MiniPascalLexer lexer = new MiniPascalLexer(input);
            AntlrErrorCollector lexicalErrorCollector = new AntlrErrorCollector(true);
            lexer.removeErrorListeners();
            lexer.addErrorListener(lexicalErrorCollector);

            MiniPascalParser parser = new MiniPascalParser(new CommonTokenStream(lexer));
            AntlrErrorCollector syntacticErrorCollector = new AntlrErrorCollector(false);
            parser.removeErrorListeners();
            parser.addErrorListener(syntacticErrorCollector);

            ParseTree tree = parser.program();
            
            if (lexicalErrorCollector.hasErrors()) {
                System.err.println("ERRO: Erros lexicais encontrados.");
                System.err.print(lexicalErrorCollector.getErrorMessages());
                return;
            }

            if (syntacticErrorCollector.hasErrors()) {
                System.err.println("ERRO: Erros sintáticos encontrados.");
                System.err.print(syntacticErrorCollector.getErrorMessages());
                return;
            }

            MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();

            System.out.println("LOG: Criando AST...");
            Program program = (Program) visitor.visit(tree);

            System.out.println("LOG: Analisando AST semanticamente...");
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(program);
            SemanticExceptionQueue semanticExceptions = semanticAnalyzer.getExceptions();

            if (!semanticExceptions.isEmpty()) {
                System.err.println("ERRO: Exceções semânticas encontradas.");
                System.err.print(semanticExceptions.getErrorMessages());
                return;
            }

            System.out.println("LOG: Gerando código...");
            CodeGen codeGen = new CodeGen(program);
            codeGen.save();
            System.out.println("LOG: Código gerado com sucesso.");

        } catch (IOException e) {
            System.err.println("ERRO: Não foi possível ler o arquivo: " + e.getMessage());
        }
    }
}
