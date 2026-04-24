package br.com.comcet.tp1.ast;

import java.util.List;

import br.com.comcet.tp1.ast.command.AssignmentCommand;
import br.com.comcet.tp1.ast.command.BlockCommand;
import br.com.comcet.tp1.ast.command.IfCommand;
import br.com.comcet.tp1.ast.command.PrintCommand;
import br.com.comcet.tp1.ast.command.WhileCommand;
import br.com.comcet.tp1.ast.declaration.VariableDeclaration;
import br.com.comcet.tp1.ast.expression.BinaryExpression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.expression.Literal;
import br.com.comcet.tp1.ast.program.Program;
import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp1.ast.type.VariableType;

public class AstCreator {
    
    public static Program createTree() {

        // PROGRAMA QUE É RETORNADO
        // var
        //     x, y : integer;      
        //     a, b : string;       
        // begin
        //     x := 5;
        //     y := 2;
        //     while (x > 0) do     
        //     begin
        //         if (x = 3) then   
        //             write(x);      
        //         else
        //             y := y + x * 2;
        //         x := x - 1;       
        //     end
        //     write(y);
        //     write(a);
        //     write(b);
        // end

        Identifier x = new Identifier("x", VariableType.INTEGER);
        Identifier y = new Identifier("y", VariableType.INTEGER);
        Identifier a = new Identifier("a", VariableType.STRING);
        Identifier b = new Identifier("b", VariableType.STRING);

        List<Identifier> identifiers = List.of(x, y);
        List<Identifier> identifiersAb = List.of(a, b);
        VariableDeclaration varDecl = new VariableDeclaration(identifiers, VariableType.INTEGER);
        VariableDeclaration varDeclAb = new VariableDeclaration(identifiersAb, VariableType.STRING);

        Literal l0 = new Literal(0, VariableType.INTEGER);
        Literal l1 = new Literal(1, VariableType.INTEGER);
        Literal l2 = new Literal(2, VariableType.INTEGER);
        Literal l3 = new Literal(3, VariableType.INTEGER);
        Literal l5 = new Literal(5, VariableType.INTEGER);

        AssignmentCommand assignX = new AssignmentCommand(x, l5);
        AssignmentCommand assignY = new AssignmentCommand(y, l2);

        PrintCommand printX = new PrintCommand(x);

        BinaryExpression x_2 = new BinaryExpression(x, l2, OperatorType.MULT, VariableType.INTEGER);
        BinaryExpression y_x_2 = new BinaryExpression(y, x_2, OperatorType.SUM, VariableType.INTEGER);
        AssignmentCommand assignY_x_2 = new AssignmentCommand(y, y_x_2);

        BinaryExpression ifCondition = new BinaryExpression(x, l3, OperatorType.EQUAL, VariableType.BOOLEAN);
        IfCommand ifCommand = new IfCommand(ifCondition, printX, assignY_x_2);

        BinaryExpression x_1 = new BinaryExpression(x, l1, OperatorType.SUB, VariableType.INTEGER);
        AssignmentCommand assignX_1 = new AssignmentCommand(x, x_1);

        BlockCommand whileBody = new BlockCommand(List.of(ifCommand, assignX_1));
        BinaryExpression whileCondition = new BinaryExpression(x, l0, OperatorType.GREATER_THAN, VariableType.BOOLEAN);
        WhileCommand whileCommand = new WhileCommand(whileCondition, whileBody);

        PrintCommand printY = new PrintCommand(y);
        PrintCommand printA = new PrintCommand(a);
        PrintCommand printB = new PrintCommand(b);

        BlockCommand block = new BlockCommand(List.of(assignX, assignY, whileCommand, printY, printA, printB));
        Program program = new Program(List.of(varDecl, varDeclAb), block);

        return program;
    }
}
