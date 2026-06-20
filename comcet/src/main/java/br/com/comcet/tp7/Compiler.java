package br.com.comcet.tp7;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;

import br.com.comcet.tp1.ast.program.Program;
import br.com.comcet.tp4.MiniPascalAntlrVisitor;
import br.com.comcet.tp4.ParseTreeCreator;
import br.com.comcet.tp5.exception.SemanticExceptionQueue;
import br.com.comcet.tp5.semantic.SemanticAnalyzer;
import br.com.comcet.tp7.code.CodeGen;

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

            ParseTree tree = ParseTreeCreator.createTree(input);
            MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();

            System.out.println("LOG: Criando AST...");
            Program program = (Program) visitor.visit(tree);

            System.out.println("LOG: Analisando AST semanticamente...");
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(program);
            SemanticExceptionQueue exceptions = semanticAnalyzer.getExceptions();

            if (!exceptions.isEmpty()) {
                printSemanticExceptions(exceptions);
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

    private static void printSemanticExceptions(SemanticExceptionQueue exceptions) {
        System.out.println("ERRO: Exceções semânticas encontradas.");
        System.out.println();
        int num = 0;
        while (!exceptions.isEmpty()) {
            System.out.println((++num) + ". " + exceptions.poll().getMessage());
        }
    }
}
