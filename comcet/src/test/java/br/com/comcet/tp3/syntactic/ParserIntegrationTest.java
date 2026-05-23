package br.com.comcet.tp3.syntactic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.comcet.tp1.ast.command.AssignmentCommand;
import br.com.comcet.tp1.ast.command.BlockCommand;
import br.com.comcet.tp1.ast.command.Command;
import br.com.comcet.tp1.ast.command.IfCommand;
import br.com.comcet.tp1.ast.command.RepeatCommand;
import br.com.comcet.tp1.ast.command.WhileCommand;
import br.com.comcet.tp1.ast.declaration.VariableDeclaration;
import br.com.comcet.tp1.ast.expression.BinaryExpression;
import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Literal;
import br.com.comcet.tp1.ast.expression.UnaryExpression;
import br.com.comcet.tp1.ast.program.Program;
import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp2.scanner.Lexer;
import br.com.comcet.tp2.scanner.charstream.StringCharStream;
import br.com.comcet.tp3.exception.ExceptionQueue;

class ParserIntegrationTest {

    @BeforeEach
    void clearExceptionQueue() {
        while (ExceptionQueue.poll() != null) {
            // drain global queue between tests
        }
    }

    @Test
    void parseProgramBuildsNameDeclarationsAndBody() {
        String code = """
                program sample;
                var x, y : integer;
                begin
                    x := 10;
                end
                """;

        Program program = parse(code);

        assertEquals("sample", program.getName());
        assertEquals(1, program.getDeclarations().getVariableDeclarations().size());

        VariableDeclaration declaration = program.getDeclarations().getVariableDeclarations().get(0);
        assertEquals(2, declaration.getIdentifiers().size());

        BlockCommand block = assertInstanceOf(BlockCommand.class, program.getBody());
        assertEquals(1, block.getCommands().size());
        assertInstanceOf(AssignmentCommand.class, block.getCommands().get(0));
    }

    @Test
    void respectsMultiplicationPrecedence() {
        String code = """
                program p;
                var x : integer;
                begin
                    x := 10 + 5 * 2;
                end
                """;

        Program program = parse(code);

        AssignmentCommand assignment = firstAssignment(program);
        BinaryExpression plus = assertInstanceOf(BinaryExpression.class, assignment.getExpression());
        assertEquals(OperatorType.SUM, plus.getOperator());

        assertInstanceOf(Literal.class, plus.getLeft());

        BinaryExpression mult = assertInstanceOf(BinaryExpression.class, plus.getRight());
        assertEquals(OperatorType.MULT, mult.getOperator());
    }

    @Test
    void parenthesesAlterPrecedence() {
        String code = """
                program p;
                var x : integer;
                begin
                    x := (10 + 5) * 2;
                end
                """;

        Program program = parse(code);

        AssignmentCommand assignment = firstAssignment(program);
        BinaryExpression mult = assertInstanceOf(BinaryExpression.class, assignment.getExpression());
        assertEquals(OperatorType.MULT, mult.getOperator());

        BinaryExpression plus = assertInstanceOf(BinaryExpression.class, mult.getLeft());
        assertEquals(OperatorType.SUM, plus.getOperator());
    }

    @Test
    void parsesIfWithElseUsingBooleanCondition() {
        String code = """
                program p;
                var flag : boolean;
                    x : integer;
                begin
                    if (flag) then x := 1; else x := 2;
                end
                """;

        Program program = parse(code);

        BlockCommand block = assertInstanceOf(BlockCommand.class, program.getBody());
        IfCommand ifCommand = assertInstanceOf(IfCommand.class, block.getCommands().get(0));

        assertNotNull(ifCommand.getThenBranch());
        assertNotNull(ifCommand.getElseBranch());
        assertInstanceOf(AssignmentCommand.class, ifCommand.getThenBranch());
        assertInstanceOf(AssignmentCommand.class, ifCommand.getElseBranch());
    }

    @Test
    void parsesWhileWithDoBody() {
        String code = """
                program p;
                var flag : boolean;
                    x : integer;
                begin
                    while (flag) do x := 1;
                end
                """;

        Program program = parse(code);

        BlockCommand block = assertInstanceOf(BlockCommand.class, program.getBody());
        WhileCommand whileCommand = assertInstanceOf(WhileCommand.class, block.getCommands().get(0));
        assertInstanceOf(AssignmentCommand.class, whileCommand.getBody());
    }

    @Test
    void parsesRepeatUntilWithBooleanCondition() {
        String code = """
                program p;
                var flag : boolean;
                    x : integer;
                begin
                    repeat x := 1; until flag
                end
                """;

        Program program = parse(code);

        BlockCommand block = assertInstanceOf(BlockCommand.class, program.getBody());
        RepeatCommand repeat = assertInstanceOf(RepeatCommand.class, block.getCommands().get(0));
        assertInstanceOf(AssignmentCommand.class, repeat.getBody());
    }

    @Test
    void parsesUnaryNotExpressionInAssignment() {
        String code = """
                program p;
                var b : boolean;
                begin
                    b := not b;
                end
                """;

        Program program = parse(code);

        AssignmentCommand assignment = firstAssignment(program);
        UnaryExpression unary = assertInstanceOf(UnaryExpression.class, assignment.getExpression());
        assertEquals(OperatorType.NOT, unary.getOperator());
    }

    @Test
    void accumulatesSyntaxErrorsAndReturnsProgram() {
        String code = """
                program p;
                var x : integer;
                begin
                    x := 1
                end
                """;

        Program program = parse(code);

        String rendered = program.toString();
        assertTrue(rendered.contains("Parser Exceptions:"));
        assertTrue(rendered.contains("SEMICOLON"));
    }

    private Program parse(String code) {
        Lexer lexer = Lexer.pascal(new StringCharStream(code));
        return Parser.parseProgram(lexer);
    }

    private AssignmentCommand firstAssignment(Program program) {
        BlockCommand block = assertInstanceOf(BlockCommand.class, program.getBody());
        Command command = block.getCommands().get(0);
        AssignmentCommand assignment = assertInstanceOf(AssignmentCommand.class, command);

        Expression expression = assignment.getExpression();
        assertNotNull(expression);

        return assignment;
    }
}
