package br.com.comcet.tp3.syntactic;

import br.com.comcet.tp1.ast.expression.BinaryExpression;
import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.expression.LiteralBoolean;
import br.com.comcet.tp1.ast.expression.LiteralInteger;
import br.com.comcet.tp1.ast.expression.LiteralString;
import br.com.comcet.tp1.ast.expression.UnaryExpression;
import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp3.exception.SintaxException;

public class ExpressionParser {

    public static Expression parseExpression(ParserInput input) {
        Expression left = parseTerm(input);
        OperatorType operator = OperatorType.fromTokenType(input.getCurrentTokenType());
        
        while (operator != null && operator.getPrecedence() == OperatorType.SUM.getPrecedence()) {
            input.advance();

            Expression right = parseTerm(input);

            left = new BinaryExpression(left.getLine(), left.getColumn(), left, right, operator);
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

            left = new BinaryExpression(left.getLine(), left.getColumn(), left, right, operator);
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
                LiteralInteger litInt = LiteralInteger.of(input.getCurrentToken());
                input.advance();
                return litInt;
            case STRING_LITERAL:
                LiteralString litStr = LiteralString.of(input.getCurrentToken());
                input.advance();
                return litStr;
            case BOOLEAN_LITERAL:
                LiteralBoolean litBool = LiteralBoolean.of(input.getCurrentToken());
                input.advance();
                return litBool;
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
                return new UnaryExpression(line, column, factor, operator);
            default:
                throw new SintaxException("Fator não reconhecido: " + input.getCurrentTokenType(),
                        input.getCurrentLine(), input.getCurrentColumn());
        }
    }
}
