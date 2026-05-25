package br.com.comcet.tp5.semantic;

import br.com.comcet.tp1.ast.program.Program;
import br.com.comcet.tp5.exception.SemanticExceptionQueue;

public class SemanticAnalyzer {

    private final SemanticContext context;
    private final CommandAnalyzer commandAnalyzer;
    private final ExpressionAnalyzer expressionAnalyzer;
    private final DeclarationAnalyzer declarationAnalyzer;

    public SemanticAnalyzer(Program program) {
        this.context = new SemanticContext();
        this.expressionAnalyzer = new ExpressionAnalyzer(this.context);
        this.commandAnalyzer = new CommandAnalyzer(this.context, this.expressionAnalyzer);
        this.declarationAnalyzer = new DeclarationAnalyzer(this.context, this.commandAnalyzer);

        visit(program);
    }

    private void visit(Program program) {
        declarationAnalyzer.visitVariableDeclarations(program.getDeclarations());
        declarationAnalyzer.visitSubroutineDeclarations(program.getSubroutineDeclarations());
        commandAnalyzer.visitBlockCommand(program.getBody());
    }

    public SemanticExceptionQueue getExceptions() {
        return context.getExceptionQueue();
    }
    
}
