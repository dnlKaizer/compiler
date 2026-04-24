package br.com.comcet.tp3.semantic;

import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp3.exception.SemanticException;

public class SemanticValidation {

    public static VariableType validateBinaryExpressionReturnType(Expression right, Expression left, OperatorType operator) {
        VariableType rightType = right.getReturnType();
        VariableType leftType = left.getReturnType();

        int line = left.getLine();
        int column = left.getColumn();

        if (!rightType.isCompatibleWith(leftType)) {
            throw new SemanticException(
                    String.format("Tipos incompatíveis para operador binário: '%s' e '%s'.", rightType, leftType),
                    line, column);
        }

        if (!rightType.supportsOperations()) {
            throw new SemanticException(
                    String.format("Operador binário não é aplicável para tipo '%s'.", rightType),
                    line, column);
        }

        if (operator.isArithmetic()) {
            if (!rightType.supportsArithmetic()) {
                throw new SemanticException(
                        String.format("Operador '%s' só é aplicável para tipo inteiro.", operator),
                        line, column);
            }

            return rightType;
        }

        if (operator.isComparison()) {
            if (!rightType.supportsComparison()) {
                throw new SemanticException(
                        String.format("Operador '%s' só é aplicável para tipo inteiro.", operator),
                        line, column);
            }

            return VariableType.BOOLEAN;
        }

        if (operator.isLogicalBinary()) {
            if (!rightType.supportsLogicalBinary()) {
                throw new SemanticException(
                        String.format("Operador '%s' só é aplicável para tipo booleano.", operator),
                        line, column);
            }

            return VariableType.BOOLEAN;
        }

        if (operator.isEquality()) {
            if (!rightType.supportsEquality()) {
                throw new SemanticException(
                        String.format("Operador '%s' não é aplicável para tipo '%s'.", operator, rightType),
                        line, column);
            }
            return VariableType.BOOLEAN;
        }

        throw new SemanticException(
                String.format("Operador '%s' não é reconhecido para validação semântica.", operator),
                line, column);
    }

    public static VariableType validateUnaryExpressionReturnType(Expression operand, OperatorType operator) {
        VariableType operandType = operand.getReturnType();
        int line = operand.getLine();
        int column = operand.getColumn();

        if (operator == OperatorType.NOT && operandType == VariableType.BOOLEAN) {
            return VariableType.BOOLEAN;
        }

        throw new SemanticException(
                String.format("Operador unário '%s' não é reconhecido para validação semântica.", operator),
                line, column);
    }

    public static VariableType validateAssignmentCompatibility(Identifier id, Expression expression) {
        VariableType variableType = id.getReturnType();
        VariableType expressionType = expression.getReturnType();
        int line = id.getLine();
        int column = id.getColumn();

        if (!expressionType.isCompatibleWith(variableType)) {
            throw new SemanticException(
                    String.format("Tipo incompatível para atribuição: '%s' não pode ser atribuído a '%s'.",
                            expressionType, variableType),
                    line, column);
        }

        return variableType;
    }

    public static void validateConditionExpression(Expression condition) {
        VariableType conditionType = condition.getReturnType();
        int line = condition.getLine();
        int column = condition.getColumn();

        if (conditionType != VariableType.BOOLEAN) {
            throw new SemanticException(
                    String.format("Expressão de condição deve ser do tipo booleano, mas é do tipo '%s'.", conditionType),
                    line, column);
        }
    }

}
