package br.com.comcet.tp1.ast.program;

import org.junit.jupiter.api.Test;

import br.com.comcet.tp1.ast.AstCreator;
import br.com.comcet.tp1.ast.command.AssignmentCommand;
import br.com.comcet.tp1.ast.command.Command;
import br.com.comcet.tp1.ast.expression.BinaryExpression;
import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.expression.LiteralInteger;
import br.com.comcet.tp1.ast.command.BlockCommand;
import br.com.comcet.tp1.ast.command.PrintCommand;
import br.com.comcet.tp1.ast.command.WhileCommand;
import br.com.comcet.tp1.ast.declaration.VariableDeclaration;
import br.com.comcet.tp1.ast.type.OperatorType;
// import br.com.comcet.tp1.ast.type.VariableType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramTest {
    
    @Test
    void testSumAssignment() {
        // Representando: x := 10 + 5;
        Expression dez = new LiteralInteger(10);
        Expression cinco = new LiteralInteger(5);
        Expression soma = new BinaryExpression(dez, cinco, OperatorType.SUM);
        Command atrib = new AssignmentCommand(new Identifier("x"), soma);

        assertTrue(atrib instanceof AssignmentCommand);
        AssignmentCommand assign = (AssignmentCommand) atrib;
        assertEquals("x", assign.getId().getName());

        assertTrue(assign.getExpression() instanceof BinaryExpression);
        BinaryExpression binary = (BinaryExpression) assign.getExpression();
        assertEquals(OperatorType.SUM, binary.getOperator());
        assertTrue(binary.getLeft() instanceof LiteralInteger);
        assertTrue(binary.getRight() instanceof LiteralInteger);

        assertEquals("x := 10 + 5;", assign.toString());
    }

    @Test
    void testAstCreator() {
        Program program = AstCreator.createTree();

        // Verificar que é uma instância de Program
        assertTrue(program instanceof Program);
        
        // Verificar declarações
        assertEquals(2, program.getDeclarations().getVariableDeclarations().size());
        
        // Primeira declaração: x, y : integer
        VariableDeclaration decl1 = program.getDeclarations().getVariableDeclarations().get(0);
        assertEquals(2, decl1.getIdentifiers().size());
        assertEquals("x", decl1.getIdentifiers().get(0).getName());
        assertEquals("y", decl1.getIdentifiers().get(1).getName());
        // assertEquals(VariableType.INTEGER, decl1.getType());
        
        // Segunda declaração: a, b : string
        VariableDeclaration decl2 = program.getDeclarations().getVariableDeclarations().get(1);
        assertEquals(2, decl2.getIdentifiers().size());
        assertEquals("a", decl2.getIdentifiers().get(0).getName());
        assertEquals("b", decl2.getIdentifiers().get(1).getName());
        // assertEquals(VariableType.STRING, decl2.getType());
        
        // Verificar body
        assertTrue(program.getBody() instanceof BlockCommand);
        BlockCommand body = (BlockCommand) program.getBody();
        
        // Body deve ter 6 comandos: 2 assignments, 1 while, 3 prints
        assertEquals(6, body.getCommands().size());
        
        // Primeiro comando: x := 5
        assertTrue(body.getCommands().get(0) instanceof AssignmentCommand);
        
        // Segundo comando: y := 2
        assertTrue(body.getCommands().get(1) instanceof AssignmentCommand);
        
        // Terceiro comando: while loop
        assertTrue(body.getCommands().get(2) instanceof WhileCommand);
        
        // Quarto, quinto e sexto comandos: print y, a, b
        assertTrue(body.getCommands().get(3) instanceof PrintCommand);
        assertTrue(body.getCommands().get(4) instanceof PrintCommand);
        assertTrue(body.getCommands().get(5) instanceof PrintCommand);
    }
}
