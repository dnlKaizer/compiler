package br.com.comcet.tp4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.comcet.tp1.ast.command.AssignmentCommand;
import br.com.comcet.tp1.ast.command.BlockCommand;
import br.com.comcet.tp1.ast.command.IfCommand;
import br.com.comcet.tp1.ast.command.WhileCommand;
import br.com.comcet.tp1.ast.expression.BinaryExpression;
import br.com.comcet.tp1.ast.expression.LiteralInteger;
import br.com.comcet.tp1.ast.type.OperatorType;

public class VisitorCommandTest {

    @Test
    public void testAssignmentCommand() {
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        MiniPascalParser.CommandContext ctx = ANTLRTestSetup.parseCommand("x := 10;");
        AssignmentCommand cmd = (AssignmentCommand) visitor.visit(ctx);

        assertEquals("x", cmd.getId().getName());
        assertTrue(cmd.getExpression() instanceof LiteralInteger);
        assertEquals(10, ((LiteralInteger) cmd.getExpression()).getValue());
    }

    @Test
    public void testIfCommand() {
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        MiniPascalParser.CommandContext ctx = ANTLRTestSetup.parseCommand("if x > 0 then x := 1 else x := 0");
        IfCommand cmd = (IfCommand) visitor.visit(ctx);

        assertTrue(cmd.getCondition() instanceof BinaryExpression);
        assertEquals(OperatorType.GREATER_THAN, ((BinaryExpression) cmd.getCondition()).getOperator());

        assertTrue(cmd.getThenBranch() instanceof AssignmentCommand);
        assertNotNull(cmd.getElseBranch());
        assertTrue(cmd.getElseBranch() instanceof AssignmentCommand);
    }

    @Test
    public void testWhileCommandAndBlock() {
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        MiniPascalParser.CommandContext ctx = ANTLRTestSetup.parseCommand("while x < 10 do begin x := x + 1; end");
        WhileCommand cmd = (WhileCommand) visitor.visit(ctx);

        assertTrue(cmd.getCondition() instanceof BinaryExpression);
        
        assertTrue(cmd.getBody() instanceof BlockCommand);
        BlockCommand block = (BlockCommand) cmd.getBody();
        assertEquals(1, block.getCommands().size());
        assertTrue(block.getCommands().get(0) instanceof AssignmentCommand);
    }
}
