package br.com.comcet.tp5.semantic;

import java.util.List;

import br.com.comcet.tp1.ast.argument.Arguments;
import br.com.comcet.tp1.ast.expression.*;
import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp3.exception.SemanticException;
import br.com.comcet.tp5.symbol.ISymbol;
import br.com.comcet.tp5.symbol.MethodSymbol;

public class ExpressionAnalyzer {

    private final SemanticContext context;

    public ExpressionAnalyzer(SemanticContext context) {
        this.context = context;
    }

    public VariableType visit(Expression expression) {
        if (expression instanceof BinaryExpression) return visitBinaryExpression((BinaryExpression) expression);
        else if (expression instanceof UnaryExpression) return visitUnaryExpression((UnaryExpression) expression);
        else if (expression instanceof FunctionCallExpression) return visitFunctionCallExpression((FunctionCallExpression) expression);
        else if (expression instanceof Identifier) return visitIdentifier((Identifier) expression);
        else if (expression instanceof LiteralBoolean) return visitLiteralBoolean((LiteralBoolean) expression);
        else if (expression instanceof LiteralInteger) return visitLiteralInteger((LiteralInteger) expression);
        else if (expression instanceof LiteralString) return visitLiteralString((LiteralString) expression);
        else throw new IllegalArgumentException("Tipo de expressão não suportado: " + expression.getClass().getSimpleName());
    }

    public VariableType visitBinaryExpression(BinaryExpression expression) {
        int line = expression.getLine();
        int column = expression.getColumn();

        VariableType left = visit(expression.getLeft());
        VariableType right = visit(expression.getRight());
        if (left == VariableType.ERROR || right == VariableType.ERROR) return VariableType.ERROR; // Evita cascata de erros

        OperatorType operator = expression.getOperator();

        if (!operator.supportsBinaryOperation(left, right)) {
            String description = "Operador '" + operator + "' não é compatível com os tipos '" + left + "' e '" + right + "'";
            context.getExceptionQueue().add(new SemanticException(description, line, column));
            return VariableType.ERROR;
        }

        return operator.getReturnType();
    }

    public VariableType visitUnaryExpression(UnaryExpression expression) {
        int line = expression.getLine();
        int column = expression.getColumn();

        VariableType operandType = visit(expression.getExpression());
        if (operandType == VariableType.ERROR) return VariableType.ERROR; // Evita cascata de erros
        
        OperatorType operator = expression.getOperator();

        if (!operator.supportsUnaryOperation(operandType)) {
            String description = "Operador '" + operator + "' não é compatível com o tipo '" + operandType + "'";
            context.getExceptionQueue().add(new SemanticException(description, line, column));
            return VariableType.ERROR;
        }

        return operator.getReturnType();
    }

    public VariableType visitFunctionCallExpression(FunctionCallExpression expression) {
        return visitFunctionCall(expression.getIdentifier(), expression.getArguments(), expression.getLine(), expression.getColumn());
    }

    public VariableType visitIdentifier(Identifier identifier) {
        VariableType type = context.getSymbolType(identifier.getName());
        if (type == VariableType.ERROR) {
            String description = "Identificador '" + identifier.getName() + "' não foi declarado neste escopo";
            context.getExceptionQueue().add(new SemanticException(description, identifier.getLine(), identifier.getColumn()));
            return VariableType.ERROR; 
        }
        return type;
    }

    public VariableType visitLiteralBoolean(LiteralBoolean literal) {
        return VariableType.BOOLEAN;
    }

    public VariableType visitLiteralInteger(LiteralInteger literal) {
        return VariableType.INTEGER;
    }

    public VariableType visitLiteralString(LiteralString literal) {
        return VariableType.STRING;
    }

    public VariableType visitFunctionCall(Identifier identifier, Arguments arguments, int line, int column) {
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

    public List<VariableType> visitArguments(Arguments arguments) {
        return arguments.getArguments().stream().map(this::visit).toList();
    }

}
