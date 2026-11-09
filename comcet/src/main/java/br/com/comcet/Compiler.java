package br.com.comcet;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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

    public void compile(String[] args) {
        log("Iniciando compilação...");

        CharStream input = readInput(args);
        if (input == null) return;

        ParseTree tree = parse(input);
        if (tree == null) return;

        Program program = buildAst(tree);
        if (program == null) return;

        if (!analyzeSemantics(program)) return;

        generateCode(program);
        log("Código gerado com sucesso.");
    }

    private CharStream readInput(String[] args) {
        String input = "";
        boolean isFilePath = false;

        // 1. Prioridade: Argumentos de Linha de Comando (CLI)
        if (args.length > 0) {
            if (args[0].equals("-f") && args.length > 1) {
                input = args[1];
                isFilePath = true;
            } else {
                input = args[0];
                // Detecção automática simples se não houver flag
                if (input.toLowerCase().endsWith(".pas") && new File(input).exists()) {
                    isFilePath = true;
                }
            }
        } 
        // 2. Fallback: Entrada Interativa (Scanner)
        else {
            Scanner keyboard = new Scanner(System.in);
            System.out.print("Digite o texto ou o caminho de um arquivo .pas: ");
            input = keyboard.nextLine();
            if (input.toLowerCase().endsWith(".pas") && new File(input).exists()) {
                isFilePath = true;
            }
            keyboard.close();
        }

        String content = "";
        if (isFilePath) {
            try {
                CharStream charStream = CharStreams.fromFileName(input);
                return charStream;
            } catch (IOException e) {
                error("Arquivo não encontrado: " + input, e.getMessage());
                return null;
            }
        } else {
            content = input;
        }

        return CharStreams.fromString(content);
    }
    
    private ParseTree parse(CharStream input) {
        log("Fazendo análise léxica e sintática...");

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
            error("Erros lexicais encontrados.", lexicalErrorCollector.getErrorMessages());
            return null;
        }

        if (syntacticErrorCollector.hasErrors()) {
            error("Erros sintáticos encontrados.", syntacticErrorCollector.getErrorMessages());
            return null;
        }

        return tree;
    }
    
    private Program buildAst(ParseTree tree) {
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        log("Criando AST...");
        return (Program) visitor.visit(tree);
    }
    
    private boolean analyzeSemantics(Program program) {
        log("Analisando AST semanticamente...");
        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(program);
        SemanticExceptionQueue semanticExceptions = semanticAnalyzer.getExceptions();

        if (!semanticExceptions.isEmpty()) {
            error("Exceções semânticas encontradas.", semanticExceptions.getErrorMessages());
            return false;
        }

        return true;
    }
    
    private void generateCode(Program program) {
        log("Gerando código...");
        CodeGen codeGen = new CodeGen(program);
        codeGen.save();
    }
    
    private void log(String message) {
        System.out.println("LOG: " + message);
    }
    
    private void error(String message, String details) {
        System.err.println("ERRO: " + message);
        System.err.println(details);
    }
    
}