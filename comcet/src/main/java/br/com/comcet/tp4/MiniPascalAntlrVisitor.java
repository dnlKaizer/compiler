package br.com.comcet.tp4;

import java.util.List;

import br.com.comcet.tp1.ast.AstNode;
import br.com.comcet.tp1.ast.argument.Arguments;
import br.com.comcet.tp1.ast.command.AssignmentCommand;
import br.com.comcet.tp1.ast.command.BlockCommand;
import br.com.comcet.tp1.ast.command.CallCommand;
import br.com.comcet.tp1.ast.command.Command;
import br.com.comcet.tp1.ast.command.IfCommand;
import br.com.comcet.tp1.ast.command.RepeatCommand;
import br.com.comcet.tp1.ast.command.WhileCommand;
import br.com.comcet.tp1.ast.declaration.ParametersDeclaration;
import br.com.comcet.tp1.ast.declaration.SubroutineDeclaration;
import br.com.comcet.tp1.ast.declaration.SubroutineDeclarations;
import br.com.comcet.tp1.ast.declaration.VariableDeclaration;
import br.com.comcet.tp1.ast.declaration.VariableDeclarations;
import br.com.comcet.tp1.ast.expression.BinaryExpression;
import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.FunctionCallExpression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.expression.LiteralBoolean;
import br.com.comcet.tp1.ast.expression.LiteralInteger;
import br.com.comcet.tp1.ast.expression.LiteralString;
import br.com.comcet.tp1.ast.expression.UnaryExpression;
import br.com.comcet.tp1.ast.program.Program;
import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp4.MiniPascalParser.ArgumentsContext;
import br.com.comcet.tp4.MiniPascalParser.AssignmentCommandContext;
import br.com.comcet.tp4.MiniPascalParser.BinaryExprContext;
import br.com.comcet.tp4.MiniPascalParser.BlockCommandContext;
import br.com.comcet.tp4.MiniPascalParser.CallCommandContext;
import br.com.comcet.tp4.MiniPascalParser.CommandContext;
import br.com.comcet.tp4.MiniPascalParser.FunctionCallContext;
import br.com.comcet.tp4.MiniPascalParser.IdentifierContext;
import br.com.comcet.tp4.MiniPascalParser.IfCommandContext;
import br.com.comcet.tp4.MiniPascalParser.LitBoolContext;
import br.com.comcet.tp4.MiniPascalParser.LitIntContext;
import br.com.comcet.tp4.MiniPascalParser.LitStrContext;
import br.com.comcet.tp4.MiniPascalParser.ParametersContext;
import br.com.comcet.tp4.MiniPascalParser.ParenExprContext;
import br.com.comcet.tp4.MiniPascalParser.ProgramContext;
import br.com.comcet.tp4.MiniPascalParser.RepeatCommandContext;
import br.com.comcet.tp4.MiniPascalParser.SubroutineDeclarationContext;
import br.com.comcet.tp4.MiniPascalParser.SubroutineDeclarationsContext;
import br.com.comcet.tp4.MiniPascalParser.UnaryExprContext;
import br.com.comcet.tp4.MiniPascalParser.VarDeclarationContext;
import br.com.comcet.tp4.MiniPascalParser.VarDeclarationsContext;
import br.com.comcet.tp4.MiniPascalParser.WhileCommandContext;

public class MiniPascalAntlrVisitor extends MiniPascalBaseVisitor<AstNode> {

    @Override
    public AstNode visitAssignmentCommand(AssignmentCommandContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        Identifier id = new Identifier(line, column, ctx.ID().getText());
        Expression expression = (Expression) visit(ctx.expression());

        return new AssignmentCommand(line, column, id, expression);
    }

    @Override
    public AstNode visitBlockCommand(BlockCommandContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        List<Command> commands = ctx.command().stream()
            .map(cmdCtx -> (Command) visit(cmdCtx))
            .toList();
        
        return new BlockCommand(line, column, commands);
    }

    @Override
    public AstNode visitCommand(CommandContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public AstNode visitIfCommand(IfCommandContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        Expression condition = (Expression) visit(ctx.expression());
        Command thenCommand = (Command) visit(ctx.command(0));
        Command elseCommand = null;

        if (ctx.command().size() > 1) {
            elseCommand = (Command) visit(ctx.command(1));
        }

        return new IfCommand(line, column, condition, thenCommand, elseCommand);
    }

    @Override
    public AstNode visitProgram(ProgramContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;
        
        String name = ctx.ID().getText();

        VariableDeclarations varDecls = ctx.varDeclarations() != null
            ? (VariableDeclarations) visit(ctx.varDeclarations())
            : new VariableDeclarations(line, column, List.of());
        
        SubroutineDeclarations subroutinesDecls = ctx.subroutineDeclarations() != null
            ? (SubroutineDeclarations) visit(ctx.subroutineDeclarations())
            : new SubroutineDeclarations(line, column, List.of());
        
        BlockCommand block = (BlockCommand) visit(ctx.blockCommand());

        return new Program(line, column, varDecls, subroutinesDecls, block, name, null);
    }

    @Override
    public AstNode visitRepeatCommand(RepeatCommandContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        Command body = (Command) visit(ctx.command());
        Expression condition = (Expression) visit(ctx.expression());
        
        return new RepeatCommand(line, column, condition, body);
    }

    @Override
    public AstNode visitVarDeclarations(VarDeclarationsContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        List<VariableDeclaration> varDecls = ctx.varDeclaration().stream()
            .map(varCtx -> (VariableDeclaration) visit(varCtx))
            .toList();

        return new VariableDeclarations(line, column, varDecls);
    }

    @Override
    public AstNode visitVarDeclaration(VarDeclarationContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        List<Identifier> ids = ctx.ID().stream()
            .map(idCtx -> new Identifier(line, column, idCtx.getText()))
            .toList();

        VariableType type = VariableType.fromString(ctx.VAR_TYPE().getText());
        
        return new VariableDeclaration(line, column, ids, type);
    }

    @Override
    public AstNode visitWhileCommand(WhileCommandContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        Expression condition = (Expression) visit(ctx.expression());
        Command body = (Command) visit(ctx.command());

        return new WhileCommand(line, column, condition, body);
    }

    @Override
    public AstNode visitSubroutineDeclarations(SubroutineDeclarationsContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        List<SubroutineDeclaration> subroutineDeclarations = ctx.subroutineDeclaration().stream()
            .map(subDeclCtx -> (SubroutineDeclaration) visit(subDeclCtx))
            .toList();
        
        return new SubroutineDeclarations(line, column, subroutineDeclarations);
    }

    @Override
    public AstNode visitSubroutineDeclaration(SubroutineDeclarationContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        Identifier id = new Identifier(line, column, ctx.ID().getText());

        ParametersDeclaration parameters = ctx.parameters() != null
            ? (ParametersDeclaration) visit(ctx.parameters())
            : new ParametersDeclaration(line, column, List.of());

        VariableType returnType = ctx.VAR_TYPE() != null
            ? VariableType.fromString(ctx.VAR_TYPE().getText())
            : VariableType.VOID;

        VariableDeclarations localVars = ctx.varDeclarations() != null
            ? (VariableDeclarations) visit(ctx.varDeclarations())
            : new VariableDeclarations(line, column, List.of());

        BlockCommand body = (BlockCommand) visit(ctx.blockCommand());

        return new SubroutineDeclaration(line, column, id, parameters, returnType, localVars, body);
    }

    @Override
    public AstNode visitParameters(ParametersContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        List<VariableDeclaration> parameters = ctx.varDeclaration().stream()
            .map(paramCtx -> (VariableDeclaration) visit(paramCtx))
            .toList();

        return new ParametersDeclaration(line, column, parameters);
    }

    @Override
    public AstNode visitCallCommand(CallCommandContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        Identifier id = new Identifier(line, column, ctx.ID().getText());

        Arguments arguments = ctx.arguments() == null ?
            new Arguments(line, column, List.of()) :
            (Arguments) visit(ctx.arguments());

        return new CallCommand(line, column, id, arguments);
    }

    @Override
    public AstNode visitArguments(ArgumentsContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        List<Expression> arguments = ctx.expression().stream()
            .map(exprCtx -> (Expression) visit(exprCtx))
            .toList();
        
        return new Arguments(line, column, arguments);
    }

    @Override
	public AstNode visitFunctionCall(FunctionCallContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        Identifier id = new Identifier(line, column, ctx.ID().getText());
        Arguments arguments = ctx.arguments() == null ?
            new Arguments(line, column, List.of()) :
            (Arguments) visit(ctx.arguments());

        return new FunctionCallExpression(line, column, id, arguments);
	}

	@Override
    public AstNode visitBinaryExpr(BinaryExprContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        Expression left = (Expression) visit(ctx.left);
        OperatorType operator = OperatorType.fromString(ctx.op.getText());
        Expression right = (Expression) visit(ctx.right);

        return new BinaryExpression(line, column, left, right, operator);
    }

    @Override
    public AstNode visitUnaryExpr(UnaryExprContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        OperatorType operator = OperatorType.fromString(ctx.op.getText());
        Expression expression = (Expression) visit(ctx.expression());

        return new UnaryExpression(line, column, expression, operator);
    }

    @Override
    public AstNode visitIdentifier(IdentifierContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        return new Identifier(line, column, ctx.ID().getText());
    }

    @Override
    public AstNode visitLitBool(LitBoolContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        return new LiteralBoolean(line, column, Boolean.parseBoolean(ctx.getChild(0).getText()));
    }

    @Override
    public AstNode visitLitInt(LitIntContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;

        return new LiteralInteger(line, column, Integer.parseInt(ctx.getChild(0).getText()));
    }

    @Override
    public AstNode visitLitStr(LitStrContext ctx) {
        int line = ctx.getStart().getLine() + 1;
        int column = ctx.getStart().getCharPositionInLine() + 1;
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
