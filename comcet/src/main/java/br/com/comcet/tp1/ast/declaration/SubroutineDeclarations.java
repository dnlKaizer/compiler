package br.com.comcet.tp1.ast.declaration;

import java.util.List;
import java.util.Objects;

import br.com.comcet.tp1.ast.command.IndentationUtil;

public class SubroutineDeclarations extends Declaration {
    List<SubroutineDeclaration> subroutineDeclarations;

    public SubroutineDeclarations(int line, int column, List<SubroutineDeclaration> subroutineDeclarations) {
        super(line, column);
        Objects.requireNonNull(subroutineDeclarations, "Lista de sub-rotinas não pode ser nula.");
        subroutineDeclarations.forEach(subDecl -> Objects.requireNonNull(subDecl, "Declaração de sub-rotina na lista não pode ser nula."));
        this.subroutineDeclarations = subroutineDeclarations;
    }

    public List<SubroutineDeclaration> getSubroutineDeclarations() {
        return subroutineDeclarations;
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;
        StringBuilder sb = new StringBuilder();
        for (SubroutineDeclaration declaration : subroutineDeclarations) {
            sb.append(declaration.toString()).append(";").append(br).append(br);
        }
        return sb.toString();
    }

}
