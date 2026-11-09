package br.com.comcet.frontend.ast.declaration;

public interface DeclarationVisitor {
    void visitSubroutineDeclaration(SubroutineDeclaration subroutineDeclaration);
    void visitVariableDeclaration(VariableDeclaration variableDeclaration);
}
