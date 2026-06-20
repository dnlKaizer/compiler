package br.com.comcet.tp1.ast;

import java.util.List;

import br.com.comcet.tp1.ast.command.AssignmentCommand;
import br.com.comcet.tp1.ast.command.BlockCommand;
import br.com.comcet.tp1.ast.command.IfCommand;
import br.com.comcet.tp1.ast.command.WriteCommand;
import br.com.comcet.tp1.ast.command.WhileCommand;
import br.com.comcet.tp1.ast.declaration.SubroutineDeclarations;
import br.com.comcet.tp1.ast.declaration.VariableDeclaration;
import br.com.comcet.tp1.ast.declaration.VariableDeclarations;
import br.com.comcet.tp1.ast.expression.BinaryExpression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.expression.LiteralInteger;
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

        Identifier x = new Identifier("x");
        Identifier y = new Identifier("y");
        Identifier a = new Identifier("a");
        Identifier b = new Identifier("b");

        List<Identifier> identifiers = List.of(x, y);
        List<Identifier> identifiersAb = List.of(a, b);
        VariableDeclaration varDecl = new VariableDeclaration(identifiers, VariableType.INTEGER);
        VariableDeclaration varDeclAb = new VariableDeclaration(identifiersAb, VariableType.STRING);

        LiteralInteger l0 = new LiteralInteger(0);
        LiteralInteger l1 = new LiteralInteger(1);
        LiteralInteger l2 = new LiteralInteger(2);
        LiteralInteger l3 = new LiteralInteger(3);
        LiteralInteger l5 = new LiteralInteger(5);

        AssignmentCommand assignX = new AssignmentCommand(x, l5);
        AssignmentCommand assignY = new AssignmentCommand(y, l2);

        WriteCommand printX = new WriteCommand(x, false);

        BinaryExpression x_2 = new BinaryExpression(x, l2, OperatorType.MULT);
        BinaryExpression y_x_2 = new BinaryExpression(y, x_2, OperatorType.SUM);
        AssignmentCommand assignY_x_2 = new AssignmentCommand(y, y_x_2);

        BinaryExpression ifCondition = new BinaryExpression(x, l3, OperatorType.EQUAL);
        IfCommand ifCommand = new IfCommand(ifCondition, printX, assignY_x_2);

        BinaryExpression x_1 = new BinaryExpression(x, l1, OperatorType.SUB);
        AssignmentCommand assignX_1 = new AssignmentCommand(x, x_1);

        BlockCommand whileBody = new BlockCommand(List.of(ifCommand, assignX_1));
        BinaryExpression whileCondition = new BinaryExpression(x, l0, OperatorType.GREATER_THAN);
        WhileCommand whileCommand = new WhileCommand(whileCondition, whileBody);

        WriteCommand printY = new WriteCommand(y, false);
        WriteCommand printA = new WriteCommand(a, false);
        WriteCommand printB = new WriteCommand(b, false);

        BlockCommand block = new BlockCommand(List.of(assignX, assignY, whileCommand, printY, printA, printB));
        VariableDeclarations variableDeclarations = new VariableDeclarations(List.of(varDecl, varDeclAb));
        SubroutineDeclarations subroutinesDeclarations = new SubroutineDeclarations(1, 1, List.of());
        Program program = new Program(variableDeclarations, subroutinesDeclarations, block);

        return program;
    }
}
