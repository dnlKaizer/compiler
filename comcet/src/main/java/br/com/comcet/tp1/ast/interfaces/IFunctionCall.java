package br.com.comcet.tp1.ast.interfaces;

import br.com.comcet.tp1.ast.argument.Arguments;
import br.com.comcet.tp1.ast.expression.Identifier;

public interface IFunctionCall {
    public Identifier getIdentifier();
    public Arguments getArguments();
}
