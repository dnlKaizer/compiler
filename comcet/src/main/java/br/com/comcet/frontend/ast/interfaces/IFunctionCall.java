package br.com.comcet.frontend.ast.interfaces;

import br.com.comcet.frontend.ast.argument.Arguments;
import br.com.comcet.frontend.ast.expression.Identifier;

public interface IFunctionCall {
    public Identifier getIdentifier();
    public Arguments getArguments();
}
