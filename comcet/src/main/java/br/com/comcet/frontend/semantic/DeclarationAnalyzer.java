package br.com.comcet.frontend.semantic;

import java.util.ArrayList;
import java.util.List;

import br.com.comcet.frontend.ast.declaration.*;
import br.com.comcet.frontend.ast.expression.Identifier;
import br.com.comcet.frontend.exception.SemanticException;
import br.com.comcet.frontend.semantic.symbol.ISymbol;
import br.com.comcet.frontend.semantic.symbol.MethodSymbol;
import br.com.comcet.frontend.semantic.symbol.VariableSymbol;
import br.com.comcet.frontend.type.VariableType;

public class DeclarationAnalyzer {

    private final SemanticContext context;
    private final CommandAnalyzer commandAnalyzer;

    public DeclarationAnalyzer(SemanticContext context, CommandAnalyzer commandAnalyzer) {
        this.context = context;
        this.commandAnalyzer = commandAnalyzer;
    }

    public void visit(Declaration declaration) {
        if (declaration instanceof ParametersDeclaration) visitParametersDeclaration((ParametersDeclaration) declaration);
        else if (declaration instanceof SubroutineDeclaration) visitSubroutineDeclaration((SubroutineDeclaration) declaration);
        else if (declaration instanceof SubroutineDeclarations) visitSubroutineDeclarations((SubroutineDeclarations) declaration);
        else if (declaration instanceof VariableDeclaration) visitVariableDeclaration((VariableDeclaration) declaration);
        else if (declaration instanceof VariableDeclarations) visitVariableDeclarations((VariableDeclarations) declaration);
        else throw new IllegalArgumentException("Tipo de declaração não suportado: " + declaration.getClass().getSimpleName());
    }

    public void visitParametersDeclaration(ParametersDeclaration declaration) {
        declaration.getVariableDeclarations().forEach(this::visitVariableDeclaration);
    }

    public void visitSubroutineDeclaration(SubroutineDeclaration declaration) {
        List<VariableType> paramTypes = new ArrayList<>();
        for (VariableDeclaration varDecl : declaration.getParameters().getVariableDeclarations()) {
            int numberOfIds = varDecl.getIdentifiers().size();
            for (int i = 0; i < numberOfIds; i++) {
                paramTypes.add(varDecl.getType());
            }
        }

        Identifier id = declaration.getIdentifier();
        id.setType(declaration.getReturnType());

        declareSymbol(new MethodSymbol(
            id.getName(),
            declaration.getReturnType(),
            paramTypes
        ));

        this.context.enterScope();

        if (declaration.isFunction()) {
            declareSymbol(new VariableSymbol(
                id.getName(), 
                declaration.getReturnType()
            ));
        }

        this.visitParametersDeclaration(declaration.getParameters());
        declaration.getLocalVariables().getVariableDeclarations().forEach((varDecl) -> {
            visitVariableDeclaration(varDecl);
        });
        this.commandAnalyzer.visitBlockCommand(declaration.getBody());

        if (declaration.isFunction()) {
            VariableSymbol funcSymbol = (VariableSymbol) this.context.getSymbol(id.getName());
            validateFunctionReturnValue(funcSymbol, id.getLine(), id.getColumn());
        }

        this.context.exitScope();
    }

    public void visitSubroutineDeclarations(SubroutineDeclarations declarations) {
        declarations.getSubroutineDeclarations().forEach(this::visitSubroutineDeclaration);
    }

    public void visitVariableDeclaration(VariableDeclaration declaration) {
        declaration.getIdentifiers().forEach((id) -> {
            id.setType(declaration.getType());
            declareSymbol(new VariableSymbol(id.getName(), declaration.getType()));
        });
    }

    public void visitVariableDeclarations(VariableDeclarations declarations) {
        declarations.getVariableDeclarations().forEach(this::visitVariableDeclaration);
    }

    private void declareSymbol(ISymbol symbol) {
        context.addSymbol(symbol);
    }

    private void validateFunctionReturnValue(VariableSymbol funcSymbol, int line, int column) {
        if (!funcSymbol.isInitialized()) {
            String description = String.format("Função '%s' deve retornar um valor do tipo '%s'", funcSymbol.getName(), funcSymbol.getType());
            context.getExceptionQueue().add(new SemanticException(description, line, column));
            return;    
        }
    }

}
