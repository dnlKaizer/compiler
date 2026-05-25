package br.com.comcet.tp5.symbol;

import br.com.comcet.tp1.ast.type.VariableType;

public interface ISymbol {
    public String getName();

    public VariableType getType();
}
