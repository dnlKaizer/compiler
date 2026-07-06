package br.com.comcet.frontend.parser;

import java.util.List;

import br.com.comcet.frontend.ast.AstNode;
import br.com.comcet.frontend.ast.NodeList;
import br.com.comcet.frontend.ast.command.AssignmentCommand;
import br.com.comcet.frontend.ast.command.BlockCommand;
import br.com.comcet.frontend.ast.command.CallCommand;
import br.com.comcet.frontend.ast.command.Command;
import br.com.comcet.frontend.ast.command.IfCommand;
import br.com.comcet.frontend.ast.command.ReadCommand;
import br.com.comcet.frontend.ast.command.RepeatCommand;
import br.com.comcet.frontend.ast.command.WhileCommand;
import br.com.comcet.frontend.ast.command.WriteCommand;
import br.com.comcet.frontend.ast.declaration.SubroutineDeclaration;
import br.com.comcet.frontend.ast.declaration.VariableDeclaration;
import br.com.comcet.frontend.ast.expression.BinaryExpression;
import br.com.comcet.frontend.ast.expression.Expression;
import br.com.comcet.frontend.ast.expression.FunctionCallExpression;
import br.com.comcet.frontend.ast.expression.Identifier;
import br.com.comcet.frontend.ast.expression.LiteralBoolean;
import br.com.comcet.frontend.ast.expression.LiteralInteger;
import br.com.comcet.frontend.ast.expression.LiteralString;
import br.com.comcet.frontend.ast.expression.UnaryExpression;
import br.com.comcet.frontend.ast.program.Program;
import br.com.comcet.frontend.parser.MiniPascalParser.*;
import br.com.comcet.frontend.type.OperatorType;
import br.com.comcet.frontend.type.VariableType;

public class MiniPascalAntlrVisitor extends MiniPascalBaseVisitor<AstNode> {

    @Override
    public AstNode visitAssignmentCommand(AssignmentCommandContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        Identifier id = new Identifier(line, column, ctx.ID().getText());
        Expression expression = (Expression) visit(ctx.expression());

        return new AssignmentCommand(line, column, id, expression);
    }

    @Override
    public AstNode visitBlockCommand(BlockCommandContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        List<Command> commands = ctx.command().stream()
            .map(cmdCtx -> (Command) visit(cmdCtx))
            .toList();
        
        NodeList<Command> commandList = new NodeList<Command>(line, column, commands);
        
        return new BlockCommand(line, column, commandList);
    }

    @Override
    public AstNode visitCommand(CommandContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public AstNode visitIfCommand(IfCommandContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        Expression condition = (Expression) visit(ctx.expression());
        Command thenCommand = (Command) visit(ctx.command(0));
        Command elseCommand = null;

        if (ctx.command().size() > 1) {
            elseCommand = (Command) visit(ctx.command(1));
        }

        return new IfCommand(line, column, condition, thenCommand, elseCommand);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AstNode visitProgram(ProgramContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        
        String name = ctx.ID().getText();

        NodeList<VariableDeclaration> varDecls = ctx.varDeclarations() != null
            ? (NodeList<VariableDeclaration>) visit(ctx.varDeclarations())
            : new NodeList<VariableDeclaration>(line, column, List.of());
        
        NodeList<SubroutineDeclaration> subroutinesDecls = ctx.subroutineDeclarations() != null
            ? (NodeList<SubroutineDeclaration>) visit(ctx.subroutineDeclarations())
            : new NodeList<SubroutineDeclaration>(line, column, List.of());
        
        BlockCommand block = (BlockCommand) visit(ctx.blockCommand());

        return new Program(line, column, varDecls, subroutinesDecls, block, name, null);
    }

    @Override
    public AstNode visitRepeatCommand(RepeatCommandContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        Command body = (Command) visit(ctx.command());
        Expression condition = (Expression) visit(ctx.expression());
        
        return new RepeatCommand(line, column, condition, body);
    }

    @Override
    public AstNode visitVarDeclarations(VarDeclarationsContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        List<VariableDeclaration> varDecls = ctx.varDeclaration().stream()
            .map(varCtx -> (VariableDeclaration) visit(varCtx))
            .toList();

        return new NodeList<VariableDeclaration>(line, column, varDecls);
    }

    @Override
    public AstNode visitReadCommand(ReadCommandContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        Identifier identifier = new Identifier(line, column, ctx.ID().getText());
        boolean isReadLn = ctx.getChild(0).getText().equalsIgnoreCase("readln");

        return new ReadCommand(line, column, identifier, isReadLn);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AstNode visitWriteCommand(WriteCommandContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        boolean isWriteLn = ctx.getChild(0).getText().equalsIgnoreCase("writeln");
        NodeList<Expression> arguments = ctx.arguments() == null ?
            new NodeList<Expression>(line, column, List.of()) :
            (NodeList<Expression>) visit(ctx.arguments());

        return new WriteCommand(line, column, arguments, isWriteLn);
    }

    @Override
    public AstNode visitVarDeclaration(VarDeclarationContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        List<Identifier> ids = ctx.ID().stream()
            .map(idCtx -> new Identifier(line, column, idCtx.getText()))
            .toList();

        VariableType type = VariableType.fromString(ctx.VAR_TYPE().getText());
        
        return new VariableDeclaration(line, column, ids, type);
    }

    @Override
    public AstNode visitWhileCommand(WhileCommandContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        Expression condition = (Expression) visit(ctx.expression());
        Command body = (Command) visit(ctx.command());

        return new WhileCommand(line, column, condition, body);
    }

    @Override
    public AstNode visitSubroutineDeclarations(SubroutineDeclarationsContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        List<SubroutineDeclaration> subroutineDeclarations = ctx.subroutineDeclaration().stream()
            .map(subDeclCtx -> (SubroutineDeclaration) visit(subDeclCtx))
            .toList();
        
        return new NodeList<SubroutineDeclaration>(line, column, subroutineDeclarations);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AstNode visitSubroutineDeclaration(SubroutineDeclarationContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        Identifier id = new Identifier(line, column, ctx.ID().getText());

        NodeList<VariableDeclaration> parameters = ctx.parameters() != null
            ? (NodeList<VariableDeclaration>) visit(ctx.parameters())
            : new NodeList<VariableDeclaration>(line, column, List.of());

        VariableType returnType = ctx.VAR_TYPE() != null
            ? VariableType.fromString(ctx.VAR_TYPE().getText())
            : VariableType.VOID;

        NodeList<VariableDeclaration> localVars = ctx.varDeclarations() != null
            ? (NodeList<VariableDeclaration>) visit(ctx.varDeclarations())
            : new NodeList<VariableDeclaration>(line, column, List.of());

        BlockCommand body = (BlockCommand) visit(ctx.blockCommand());

        return new SubroutineDeclaration(line, column, id, parameters, returnType, localVars, body);
    }

    @Override
    public AstNode visitParameters(ParametersContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        List<VariableDeclaration> parameters = ctx.varDeclaration().stream()
            .map(paramCtx -> (VariableDeclaration) visit(paramCtx))
            .toList();

        return new NodeList<VariableDeclaration>(line, column, parameters);
    }

    @SuppressWarnings("unchecked")
    @Override
    public AstNode visitCallCommand(CallCommandContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        Identifier id = new Identifier(line, column, ctx.ID().getText());

        NodeList<Expression> arguments = ctx.arguments() == null ?
            new NodeList<Expression>(line, column, List.of()) :
            (NodeList<Expression>) visit(ctx.arguments());

        return new CallCommand(line, column, id, arguments);
    }

    @Override
    public AstNode visitArguments(ArgumentsContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        List<Expression> arguments = ctx.expression().stream()
            .map(exprCtx -> (Expression) visit(exprCtx))
            .toList();
        
        return new NodeList<Expression>(line, column, arguments);
    }

    @SuppressWarnings("unchecked")
    @Override
	public AstNode visitFunctionCall(FunctionCallContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        Identifier id = new Identifier(line, column, ctx.ID().getText());
        NodeList<Expression> arguments = ctx.arguments() == null ?
            new NodeList<Expression>(line, column, List.of()) :
            (NodeList<Expression>) visit(ctx.arguments());

        return new FunctionCallExpression(line, column, id, arguments);
	}

	@Override
    public AstNode visitBinaryExpr(BinaryExprContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        Expression left = (Expression) visit(ctx.left);
        OperatorType operator = OperatorType.fromString(ctx.op.getText());
        Expression right = (Expression) visit(ctx.right);

        return new BinaryExpression(line, column, left, right, operator);
    }

    @Override
    public AstNode visitUnaryExpr(UnaryExprContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        OperatorType operator = OperatorType.fromString(ctx.op.getText());
        Expression expression = (Expression) visit(ctx.expression());

        return new UnaryExpression(line, column, expression, operator);
    }

    @Override
    public AstNode visitIdentifier(IdentifierContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        return new Identifier(line, column, ctx.ID().getText());
    }

    @Override
    public AstNode visitLitBool(LitBoolContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        return new LiteralBoolean(line, column, Boolean.parseBoolean(ctx.getChild(0).getText()));
    }

    @Override
    public AstNode visitLitInt(LitIntContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        return new LiteralInteger(line, column, Integer.parseInt(ctx.getChild(0).getText()));
    }

    @Override
    public AstNode visitLitStr(LitStrContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();
        String text = ctx.getChild(0).getText();
        text = text.substring(1, text.length() - 1);
        text = text.replace("\\n", "\n")
                    .replace("\\t", "\t")
                    .replace("\\r", "\r")
                    .replace("\\\'", "\'")
                    .replace("\\\\", "\\");

        return new LiteralString(line, column, text);
    }

    @Override
    public AstNode visitParenExpr(ParenExprContext ctx) {
        return (Expression) visit(ctx.expression());
    }

}
