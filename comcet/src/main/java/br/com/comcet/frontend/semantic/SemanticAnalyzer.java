package br.com.comcet.frontend.semantic;

import br.com.comcet.frontend.ast.program.Program;
import br.com.comcet.frontend.exception.SemanticExceptionQueue;

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
        program.getDeclarations().getItems().forEach(decl -> declarationAnalyzer.visit(decl));
        program.getSubroutineDeclarations().getItems().forEach(subDecl -> declarationAnalyzer.visit(subDecl));
        commandAnalyzer.visitBlockCommand(program.getBody());
    }

    public SemanticExceptionQueue getExceptions() {
        return context.getExceptionQueue();
    }
    
}
