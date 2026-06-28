package br.com.comcet.backend.bytecode;

import br.com.comcet.backend.context.CodeGenContext;
import br.com.comcet.backend.manager.SubroutineManager;
import br.com.comcet.backend.manager.VariableManager;
import br.com.comcet.frontend.ast.declaration.SubroutineDeclaration;
import br.com.comcet.frontend.ast.declaration.SubroutineDeclarations;
import br.com.comcet.frontend.ast.declaration.VariableDeclaration;
import br.com.comcet.frontend.ast.declaration.VariableDeclarations;

public class DeclarationCodeGen {

    private final CodeGenContext codeGenContext;

    public DeclarationCodeGen(CodeGenContext codeGenContext) {
        this.codeGenContext = codeGenContext;
    }

    public void generateLocalVariableDeclarations(VariableDeclarations variableDeclarations) {
        variableDeclarations.getVariableDeclarations().forEach(this::generateLocalVariableDeclaration);
    }

    public void generateLocalVariableDeclaration(VariableDeclaration variableDeclaration) {
        variableDeclaration.getIdentifiers().forEach(identifier -> {
            VariableManager.declareLocal(codeGenContext, identifier);
        });
    }

    public void generateGlobalVariableDeclarations(VariableDeclarations variableDeclarations) {
        variableDeclarations.getVariableDeclarations().forEach(this::generateGlobalVariableDeclaration);
    }

    public void generateGlobalVariableDeclaration(VariableDeclaration variableDeclaration) {
        variableDeclaration.getIdentifiers().forEach(identifier -> {
            VariableManager.declareGlobal(codeGenContext, identifier);
        });
    }

    public void generateSubroutineDeclarations(SubroutineDeclarations subroutineDeclarations) {
        subroutineDeclarations.getSubroutineDeclarations().forEach(this::generateSubroutineDeclaration);
    }

    public void generateSubroutineDeclaration(SubroutineDeclaration subroutineDeclaration) {
        SubroutineManager.declareSubroutine(codeGenContext, subroutineDeclaration);
    }

}
