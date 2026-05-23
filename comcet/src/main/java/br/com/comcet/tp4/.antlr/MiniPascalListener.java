// Generated from c:/Danilo/Projetos/compiler/comcet/src/main/java/br/com/comcet/tp4/MiniPascal.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MiniPascalParser}.
 */
public interface MiniPascalListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MiniPascalParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MiniPascalParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniPascalParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MiniPascalParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniPascalParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(MiniPascalParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniPascalParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(MiniPascalParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniPascalParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MiniPascalParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniPascalParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MiniPascalParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniPascalParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(MiniPascalParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniPascalParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(MiniPascalParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniPascalParser#assignmentCommand}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentCommand(MiniPascalParser.AssignmentCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniPascalParser#assignmentCommand}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentCommand(MiniPascalParser.AssignmentCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniPascalParser#blockCommand}.
	 * @param ctx the parse tree
	 */
	void enterBlockCommand(MiniPascalParser.BlockCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniPascalParser#blockCommand}.
	 * @param ctx the parse tree
	 */
	void exitBlockCommand(MiniPascalParser.BlockCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniPascalParser#ifCommand}.
	 * @param ctx the parse tree
	 */
	void enterIfCommand(MiniPascalParser.IfCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniPascalParser#ifCommand}.
	 * @param ctx the parse tree
	 */
	void exitIfCommand(MiniPascalParser.IfCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniPascalParser#whileCommand}.
	 * @param ctx the parse tree
	 */
	void enterWhileCommand(MiniPascalParser.WhileCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniPascalParser#whileCommand}.
	 * @param ctx the parse tree
	 */
	void exitWhileCommand(MiniPascalParser.WhileCommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MiniPascalParser#repeatCommand}.
	 * @param ctx the parse tree
	 */
	void enterRepeatCommand(MiniPascalParser.RepeatCommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MiniPascalParser#repeatCommand}.
	 * @param ctx the parse tree
	 */
	void exitRepeatCommand(MiniPascalParser.RepeatCommandContext ctx);
}