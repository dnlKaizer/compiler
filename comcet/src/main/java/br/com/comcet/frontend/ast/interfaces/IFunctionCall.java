package br.com.comcet.frontend.ast.interfaces;

import java.util.List;

import br.com.comcet.frontend.ast.expression.Expression;
import br.com.comcet.frontend.ast.expression.Identifier;

public interface IFunctionCall {
    public Identifier getIdentifier();
    public List<Expression> getArguments();
}
