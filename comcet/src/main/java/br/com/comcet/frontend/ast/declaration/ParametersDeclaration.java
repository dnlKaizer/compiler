package br.com.comcet.frontend.ast.declaration;

import java.util.List;

public class ParametersDeclaration extends VariableDeclarations {

    public ParametersDeclaration(int line, int column, List<VariableDeclaration> variableDeclarations) {
        super(line, column, variableDeclarations);
    }

    public ParametersDeclaration(List<VariableDeclaration> variableDeclarations) {
        super(variableDeclarations);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (VariableDeclaration declaration : getVariableDeclarations()) {
            sb.append(declaration.toString()).append(" ");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1);
        return sb.toString();
    }

}
