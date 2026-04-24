package br.com.comcet.tp3.syntactic;

import br.com.comcet.tp1.ast.expression.BinaryExpression;
import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.expression.Literal;
import br.com.comcet.tp1.ast.expression.UnaryExpression;
import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp3.exception.SintaxException;
import br.com.comcet.tp3.semantic.SemanticValidation;

public class ExpressionParser {

    public static Expression parseExpression(ParserInput input) {
        Expression left = parseTerm(input);
        OperatorType operator = OperatorType.fromTokenType(input.getCurrentTokenType());
        
        while (operator != null && operator.getPrecedence() == OperatorType.SUM.getPrecedence()) {
            input.advance();

            Expression right = parseTerm(input);
            VariableType returnType = SemanticValidation.validateBinaryExpressionReturnType(right, left, operator);

            left = new BinaryExpression(left.getLine(), left.getColumn(), left, right, operator, returnType);
            operator = OperatorType.fromTokenType(input.getCurrentTokenType());
        }

        return left;
    }

    public static Expression parseTerm(ParserInput input) {
        Expression left = parseFactor(input);
        OperatorType operator = OperatorType.fromTokenType(input.getCurrentTokenType());

        while (operator != null && operator.getPrecedence() == OperatorType.MULT.getPrecedence()) {
            input.advance();

            Expression right = parseFactor(input);
            VariableType returnType = SemanticValidation.validateBinaryExpressionReturnType(right, left, operator);

            left = new BinaryExpression(left.getLine(), left.getColumn(), left, right, operator, returnType);
            operator = OperatorType.fromTokenType(input.getCurrentTokenType());
        }

        return left;
    }

    public static Expression parseFactor(ParserInput input) {
        switch (input.getCurrentTokenType()) {
            case ID:
                Identifier id = input.getIdentifier(input.getCurrentToken());
                input.advance();
                return id;
            case INTEGER_LITERAL:
            case STRING_LITERAL:
            case BOOLEAN_LITERAL:
                Literal lit = new Literal(input.getCurrentToken());
                input.advance();
                return lit;
            case LPAREN:
                input.advance();
                Expression expr = parseExpression(input);
                input.match(TokenType.RPAREN);
                input.advance();
                return expr;
            case NOT:
                OperatorType operator = OperatorType.fromTokenType(input.getCurrentTokenType());
                int line = input.getCurrentLine();
                int column = input.getCurrentColumn();
                input.advance();
                Expression factor = parseFactor(input);
                VariableType returnType = SemanticValidation.validateUnaryExpressionReturnType(factor, operator);
                return new UnaryExpression(line, column, factor, operator, returnType);
            default:
                throw new SintaxException("Fator não reconhecido: " + input.getCurrentTokenType(),
                        input.getCurrentLine(), input.getCurrentColumn());
        }
    }
}
