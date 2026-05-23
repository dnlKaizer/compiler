package br.com.comcet.tp4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.comcet.tp1.ast.expression.BinaryExpression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.expression.LiteralBoolean;
import br.com.comcet.tp1.ast.expression.LiteralInteger;
import br.com.comcet.tp1.ast.expression.UnaryExpression;
import br.com.comcet.tp1.ast.type.OperatorType;

public class VisitorExpressionTest {

    @Test
    public void testLiteralAndIdentifier() {
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        MiniPascalParser.ExpressionContext ctxInt = ANTLRTestSetup.parseExpression("42");
        LiteralInteger litInt = (LiteralInteger) visitor.visit(ctxInt);
        assertEquals(42, litInt.getValue());

        MiniPascalParser.ExpressionContext ctxBool = ANTLRTestSetup.parseExpression("true");
        LiteralBoolean litBool = (LiteralBoolean) visitor.visit(ctxBool);
        assertTrue(litBool.getValue());

        MiniPascalParser.ExpressionContext ctxId = ANTLRTestSetup.parseExpression("minhaVariavel");
        Identifier id = (Identifier) visitor.visit(ctxId);
        assertEquals("minhaVariavel", id.getName());
    }

    @Test
    public void testUnaryExpression() {
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        MiniPascalParser.ExpressionContext ctx = ANTLRTestSetup.parseExpression("not false");
        UnaryExpression expr = (UnaryExpression) visitor.visit(ctx);
        
        assertEquals(OperatorType.NOT, expr.getOperator());
        assertTrue(expr.getExpression() instanceof LiteralBoolean);
    }

    @Test
    public void testSimpleBinaryExpression() {
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        MiniPascalParser.ExpressionContext ctx = ANTLRTestSetup.parseExpression("x + 5");
        BinaryExpression expr = (BinaryExpression) visitor.visit(ctx);

        assertEquals(OperatorType.SUM, expr.getOperator());
        assertTrue(expr.getLeft() instanceof Identifier);
        assertTrue(expr.getRight() instanceof LiteralInteger);
        assertEquals("x", ((Identifier) expr.getLeft()).getName());
        assertEquals(5, ((LiteralInteger) expr.getRight()).getValue());
    }

    @Test
    public void testPrecedence() {
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        // Expressão: 2 + 3 * 4
        // O Parser deve colocar a SUM (+) mais acima e a MULT (*) como filho direito.
        MiniPascalParser.ExpressionContext ctx = ANTLRTestSetup.parseExpression("2 + 3 * 4");
        BinaryExpression root = (BinaryExpression) visitor.visit(ctx);
        
        assertEquals(OperatorType.SUM, root.getOperator());
        assertEquals(2, ((LiteralInteger) root.getLeft()).getValue());
        
        BinaryExpression rightChild = (BinaryExpression) root.getRight();
        assertEquals(OperatorType.MULT, rightChild.getOperator());
        assertEquals(3, ((LiteralInteger) rightChild.getLeft()).getValue());
        assertEquals(4, ((LiteralInteger) rightChild.getRight()).getValue());
    }
}
