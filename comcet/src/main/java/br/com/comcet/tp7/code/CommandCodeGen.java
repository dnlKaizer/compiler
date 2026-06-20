package br.com.comcet.tp7.code;

import java.util.List;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import br.com.comcet.tp1.ast.command.*;
import br.com.comcet.tp1.ast.expression.Expression;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp7.context.MethodContext;
import br.com.comcet.tp7.manager.ScannerManager;
import br.com.comcet.tp7.manager.VariableManager;
import br.com.comcet.tp7.mapper.DescriptorMapper;

public class CommandCodeGen {

    private final MethodContext methodContext;
    private final ExpressionCodeGen expressionCodeGen;

    public CommandCodeGen(MethodContext methodContext) {
        this.methodContext = methodContext;
        this.expressionCodeGen = new ExpressionCodeGen(methodContext);
    }

    public void generateCommand(Command command) {
        if (command instanceof AssignmentCommand) generateAssignmentCommand((AssignmentCommand) command);
        else if (command instanceof BlockCommand) generateBlockCommand((BlockCommand) command);
        else if (command instanceof CallCommand) generateCallCommand((CallCommand) command);
        else if (command instanceof IfCommand) generateIfCommand((IfCommand) command);
        else if (command instanceof ReadCommand) generateReadCommand((ReadCommand) command);
        else if (command instanceof RepeatCommand) generateRepeatCommand((RepeatCommand) command);
        else if (command instanceof WhileCommand) generateWhileCommand((WhileCommand) command);
        else if (command instanceof WriteCommand) generateWriteCommand((WriteCommand) command);
        else throw new IllegalArgumentException("Tipo de comando desconhecido: " + command.getClass().getSimpleName());
    }

    public void generateAssignmentCommand(AssignmentCommand assignmentCommand) {
        Identifier identifier = assignmentCommand.getId();
        Expression expression = assignmentCommand.getExpression();

        expressionCodeGen.generateExpression(expression);

        VariableManager.store(methodContext, identifier);
    }

    public void generateBlockCommand(BlockCommand blockCommand) {
        blockCommand.getCommands().forEach(this::generateCommand);
    }

    public void generateCallCommand(CallCommand callCommand) {
        expressionCodeGen.generateFunctionCall(callCommand);
        Identifier identifier = callCommand.getIdentifier();
        String jvmDescriptor = methodContext.getVariableSymbolTable().getGlobalVariableDescriptor(identifier);
        String voidDescriptor = DescriptorMapper.inferType(VariableType.VOID);
        if (!jvmDescriptor.equals(voidDescriptor)) methodContext.getMethodVisitor().visitInsn(Opcodes.POP);
    }

    public void generateIfCommand(IfCommand ifCommand) {
        Label elseLabel = new Label();
        Label endLabel = new Label();

        expressionCodeGen.generateExpression(ifCommand.getCondition());

        methodContext.getMethodVisitor().visitJumpInsn(Opcodes.IFEQ, elseLabel);
        generateCommand(ifCommand.getThenBranch());
        methodContext.getMethodVisitor().visitJumpInsn(Opcodes.GOTO, endLabel);

        methodContext.getMethodVisitor().visitLabel(elseLabel);
        if (ifCommand.getElseBranch() != null) generateCommand(ifCommand.getElseBranch());
        methodContext.getMethodVisitor().visitLabel(endLabel);
    }

    public void generateReadCommand(ReadCommand readCommand) {
        Identifier identifier = readCommand.getIdentifier();

        ScannerManager.read(methodContext, identifier.getType());
        VariableManager.store(methodContext, identifier);

        if (readCommand.isNewLine()) ScannerManager.skipLine(methodContext);
    }

    public void generateRepeatCommand(RepeatCommand repeatCommand) {
        Label repeatLabel = new Label();

        methodContext.getMethodVisitor().visitLabel(repeatLabel);
        generateCommand(repeatCommand.getBody());
        expressionCodeGen.generateExpression(repeatCommand.getCondition());
        methodContext.getMethodVisitor().visitJumpInsn(Opcodes.IFEQ, repeatLabel);
    }

    public void generateWhileCommand(WhileCommand whileCommand) {
        Label whileLabel = new Label();
        Label endLabel = new Label();

        methodContext.getMethodVisitor().visitLabel(whileLabel);
        expressionCodeGen.generateExpression(whileCommand.getCondition());
        methodContext.getMethodVisitor().visitJumpInsn(Opcodes.IFEQ, endLabel);
        generateCommand(whileCommand.getBody());
        methodContext.getMethodVisitor().visitJumpInsn(Opcodes.GOTO, whileLabel);
        methodContext.getMethodVisitor().visitLabel(endLabel);
    }

    public void generateWriteCommand(WriteCommand writeCommand) {
        String name = "print";
        List<Expression> expressions = writeCommand.getArguments().getExpressions();
        int numExpressions = expressions.size();
        MethodVisitor methodVisitor = methodContext.getMethodVisitor();

        for (int i = 0; i < numExpressions; i++) {
            if (i == numExpressions - 1)
                name = writeCommand.isNewLine() ? "println" : "print";

            Expression expression = expressions.get(i);
            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            expressionCodeGen.generateExpression(expression);
            convertToStringIfNeeded(expression);
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", name, "(Ljava/lang/String;)V", false);
        }
    }

    private void convertToStringIfNeeded(Expression expression) {
        VariableType type = expression.getType();
        if (type != VariableType.STRING) {
            String jvmDescriptor = DescriptorMapper.inferType(type);
            methodContext.getMethodVisitor().visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "java/lang/String",
                    "valueOf",
                    "(" + jvmDescriptor + ")Ljava/lang/String;",
                    false);
        }
    }
}
