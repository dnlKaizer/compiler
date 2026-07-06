package br.com.comcet.backend.bytecode;

import br.com.comcet.backend.context.CodeGenContext;
import br.com.comcet.backend.manager.SubroutineManager;
import br.com.comcet.backend.manager.VariableManager;
import br.com.comcet.frontend.ast.declaration.SubroutineDeclaration;
import br.com.comcet.frontend.ast.declaration.VariableDeclaration;

public class DeclarationCodeGen {

    private final CodeGenContext codeGenContext;

    public DeclarationCodeGen(CodeGenContext codeGenContext) {
        this.codeGenContext = codeGenContext;
    }

    public void generateLocalVariableDeclaration(VariableDeclaration variableDeclaration) {
        variableDeclaration.getIdentifiers().forEach(identifier -> {
            VariableManager.declareLocal(codeGenContext, identifier);
        });
    }

    public void generateGlobalVariableDeclaration(VariableDeclaration variableDeclaration) {
        variableDeclaration.getIdentifiers().forEach(identifier -> {
            VariableManager.declareGlobal(codeGenContext, identifier);
        });
    }

    public void generateSubroutineDeclaration(SubroutineDeclaration subroutineDeclaration) {
        SubroutineManager.declareSubroutine(codeGenContext, subroutineDeclaration);
    }

}
