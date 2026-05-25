package br.com.comcet.tp4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.comcet.tp1.ast.command.AssignmentCommand;
import br.com.comcet.tp1.ast.command.BlockCommand;
import br.com.comcet.tp1.ast.program.Program;

public class PipelineIntegrationTest {

    @Test
    public void testProgramIntegration() {
        String code = 
            "program IntegrationTest;\n" +
            "var \n" +
            "   x, y : integer;\n" +
            "   active: boolean;\n" +
            "begin\n" +
            "   x := 10;\n" +
            "   active := true;\n" +
            "end.";
        
        var tree = ParseTreeCreator.createTree(code);
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        
        Program ast = (Program) visitor.visit(tree);

        assertNotNull(ast, "AST do Program falhou na criacao");
        assertEquals("IntegrationTest", ast.getName());
        
        assertNotNull(ast.getDeclarations(), "As decls não poderiam ser nulas");
        
        // Verifica se ambas as declarações foram puxadas: integer (x, y) e boolean (active)
        assertEquals(2, ast.getDeclarations().getVariableDeclarations().size());

        assertTrue(ast.getBody() instanceof BlockCommand);
        BlockCommand body = (BlockCommand) ast.getBody();
        assertEquals(2, body.getCommands().size());

        AssignmentCommand firstCmd = (AssignmentCommand) body.getCommands().get(0);
        assertEquals("x", firstCmd.getId().getName());
        
        AssignmentCommand secondCmd = (AssignmentCommand) body.getCommands().get(1);
        assertEquals("active", secondCmd.getId().getName());
    }
}
