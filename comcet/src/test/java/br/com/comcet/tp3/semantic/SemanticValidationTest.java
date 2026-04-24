package br.com.comcet.tp3.semantic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.expression.Literal;
import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp3.exception.SemanticException;

class SemanticValidationTest {

    @Test
    void validateBinaryArithmeticReturnsInteger() {
        Expression left = new Literal(10, VariableType.INTEGER);
        Expression right = new Literal(5, VariableType.INTEGER);

        VariableType result = SemanticValidation.validateBinaryExpressionReturnType(right, left, OperatorType.SUM);

        assertEquals(VariableType.INTEGER, result);
    }

    @Test
    void validateBinaryComparisonReturnsBoolean() {
        Expression left = new Literal(10, VariableType.INTEGER);
        Expression right = new Literal(5, VariableType.INTEGER);

        VariableType result = SemanticValidation.validateBinaryExpressionReturnType(right, left, OperatorType.LESS_THAN);

        assertEquals(VariableType.BOOLEAN, result);
    }

    @Test
    void validateBinaryThrowsForIncompatibleTypes() {
        Expression left = new Literal("abc", VariableType.STRING);
        Expression right = new Literal(5, VariableType.INTEGER);

        assertThrows(SemanticException.class,
                () -> SemanticValidation.validateBinaryExpressionReturnType(right, left, OperatorType.SUM));
    }

    @Test
    void validateUnaryNotReturnsBooleanForBooleanOperand() {
        Expression operand = new Literal(true, VariableType.BOOLEAN);

        VariableType result = SemanticValidation.validateUnaryExpressionReturnType(operand, OperatorType.NOT);

        assertEquals(VariableType.BOOLEAN, result);
    }

    @Test
    void validateUnaryNotThrowsForIntegerOperand() {
        Expression operand = new Literal(1, VariableType.INTEGER);

        assertThrows(SemanticException.class,
                () -> SemanticValidation.validateUnaryExpressionReturnType(operand, OperatorType.NOT));
    }

    @Test
    void validateAssignmentCompatibilityReturnsVariableTypeWhenCompatible() {
        Identifier id = new Identifier("x", VariableType.INTEGER);
        Expression expression = new Literal(7, VariableType.INTEGER);

        VariableType result = SemanticValidation.validateAssignmentCompatibility(id, expression);

        assertEquals(VariableType.INTEGER, result);
    }

    @Test
    void validateAssignmentCompatibilityThrowsWhenIncompatible() {
        Identifier id = new Identifier("x", VariableType.INTEGER);
        Expression expression = new Literal(true, VariableType.BOOLEAN);

        assertThrows(SemanticException.class,
                () -> SemanticValidation.validateAssignmentCompatibility(id, expression));
    }

    @Test
    void validateConditionExpressionThrowsWhenNotBoolean() {
        Expression condition = new Literal(1, VariableType.INTEGER);

        assertThrows(SemanticException.class,
                () -> SemanticValidation.validateConditionExpression(condition));
    }
}
