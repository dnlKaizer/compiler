package br.com.comcet.frontend.semantic;

import java.util.List;

import br.com.comcet.frontend.ast.expression.*;
import br.com.comcet.frontend.exception.SemanticException;
import br.com.comcet.frontend.semantic.symbol.ISymbol;
import br.com.comcet.frontend.semantic.symbol.MethodSymbol;
import br.com.comcet.frontend.type.OperatorType;
import br.com.comcet.frontend.type.VariableType;

public class ExpressionAnalyzer implements ExpressionVisitor<VariableType> {

    private final SemanticContext context;

    public ExpressionAnalyzer(SemanticContext context) {
        this.context = context;
    }

    @Override
    public VariableType visitBinaryExpression(BinaryExpression expression) {
        int line = expression.getLine();
        int column = expression.getColumn();

        VariableType left = expression.getLeft().accept(this);
        VariableType right = expression.getRight().accept(this);
        if (left == VariableType.ERROR || right == VariableType.ERROR) return expression.setType(VariableType.ERROR);

        OperatorType operator = expression.getOperator();

        if (!operator.supportsBinaryOperation(left, right)) {
            String description = "Operador '" + operator + "' não é compatível com os tipos '" + left + "' e '" + right + "'";
            context.getExceptionQueue().add(new SemanticException(description, line, column));
            return expression.setType(VariableType.ERROR);
        }

        if (left == VariableType.STRING) return expression.setType(VariableType.STRING);
        
        return expression.setType(operator.getResultType(left, right));
    }

    @Override
    public VariableType visitUnaryExpression(UnaryExpression expression) {
        int line = expression.getLine();
        int column = expression.getColumn();

        VariableType operandType = expression.getExpression().accept(this);
        if (operandType == VariableType.ERROR) return expression.setType(VariableType.ERROR); // Evita cascata de erros
        
        OperatorType operator = expression.getOperator();

        if (!operator.supportsUnaryOperation(operandType)) {
            String description = "Operador '" + operator + "' não é compatível com o tipo '" + operandType + "'";
            context.getExceptionQueue().add(new SemanticException(description, line, column));
            return expression.setType(VariableType.ERROR);
        }

        return expression.setType(operator.getResultType(operandType));
    }

    @Override
    public VariableType visitFunctionCallExpression(FunctionCallExpression expression) {
        VariableType type = visitFunctionCall(expression.getIdentifier(), expression.getArguments(), expression.getLine(), expression.getColumn());
        return expression.setType(type);
    }

    @Override
    public VariableType visitIdentifier(Identifier identifier) {
        VariableType type = context.getSymbolType(identifier.getName());
        if (type == VariableType.ERROR) {
            String description = "Identificador '" + identifier.getName() + "' não foi declarado neste escopo";
            context.getExceptionQueue().add(new SemanticException(description, identifier.getLine(), identifier.getColumn()));
            return identifier.setType(VariableType.ERROR);
        }
        return identifier.setType(type);
    }

    @Override
    public VariableType visitLiteralBoolean(LiteralBoolean literal) {
        return literal.setType(VariableType.BOOLEAN);
    }

    @Override
    public VariableType visitLiteralInteger(LiteralInteger literal) {
        return literal.setType(VariableType.INTEGER);
    }

    @Override
    public VariableType visitLiteralString(LiteralString literal) {
        return literal.setType(VariableType.STRING);
    }

    public VariableType visitFunctionCall(Identifier identifier, List<Expression> arguments, int line, int column) {
        ISymbol symbol = context.getSymbol(identifier.getName());
        if (symbol == null) {
            String description = String.format("Identificador '%s' não declarado", identifier.getName());
            context.addException(new SemanticException(description, line, column));
            return VariableType.ERROR;
        }

        // Se for uma chamada recursiva, o símbolo do método estará no escopo pai
        if (!(symbol instanceof MethodSymbol)) {
            symbol = context.getSymbolFromFatherScope(identifier.getName());
        }

        if (!(symbol instanceof MethodSymbol)) {
            String description = String.format("Identificador '%s' não é um método", identifier.getName());
            context.addException(new SemanticException(description, line, column));
            return VariableType.ERROR;
        }

        MethodSymbol methodSymbol = (MethodSymbol) symbol;

        List<VariableType> parameters = methodSymbol.getParameters();
        int numberOfParameters = parameters.size();
        List<VariableType> argumentTypes = visitArguments(arguments);
        int numberOfArguments = argumentTypes.size();

        if (numberOfParameters != numberOfArguments) {
            String description = String.format("Número de argumentos fornecidos (%d) não corresponde ao número de parâmetros esperados (%d) para o método '%s'",
                numberOfArguments, numberOfParameters, identifier.getName());
            context.addException(new SemanticException(description, line, column));
            return VariableType.ERROR;
        }

        for (int i = 0; i < numberOfArguments; i++) {
            if (parameters.get(i) != argumentTypes.get(i)) {
                String description = String.format("Tipo do argumento %d é '%s', mas o parâmetro é do tipo '%s'",
                    i + 1, argumentTypes.get(i), parameters.get(i));
                context.addException(new SemanticException(description, line, column));
            }
        }

        return methodSymbol.getType();
    }

    public List<VariableType> visitArguments(List<Expression> arguments) {
        return arguments.stream().map(expression -> expression.accept(this)).toList();
    }

}
