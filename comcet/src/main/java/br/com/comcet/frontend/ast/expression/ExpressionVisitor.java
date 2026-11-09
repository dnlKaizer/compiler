package br.com.comcet.frontend.ast.expression;

public interface ExpressionVisitor<R> {
    R visitBinaryExpression(BinaryExpression expression);
    R visitUnaryExpression(UnaryExpression expression);
    R visitFunctionCallExpression(FunctionCallExpression expression);
    R visitIdentifier(Identifier identifier);
    R visitLiteralBoolean(LiteralBoolean literal);
    R visitLiteralInteger(LiteralInteger literal);
    R visitLiteralString(LiteralString literal);
}