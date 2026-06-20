package br.com.comcet.tp7.code;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

import br.com.comcet.tp1.ast.expression.*;
import br.com.comcet.tp1.ast.interfaces.IFunctionCall;
import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp1.ast.type.VariableType;

import br.com.comcet.tp7.context.MethodContext;
import br.com.comcet.tp7.manager.VariableManager;
import br.com.comcet.tp7.mapper.DescriptorMapper;
import br.com.comcet.tp7.mapper.OpcodeMapper;

public class ExpressionCodeGen {

    private final MethodContext methodContext;

    public ExpressionCodeGen(MethodContext methodContext) {
        this.methodContext = methodContext;
    }

    public void generateExpression(Expression expression) {
        if (expression instanceof BinaryExpression) generateBinaryExpression((BinaryExpression) expression);
        else if (expression instanceof UnaryExpression) generateUnaryExpression((UnaryExpression) expression);
        else if (expression instanceof FunctionCallExpression) generateFunctionCallExpression((FunctionCallExpression) expression);
        else if (expression instanceof Identifier) generateIdentifier((Identifier) expression);
        else if (expression instanceof Literal<?>) generateLiteral((Literal<?>) expression);
        else throw new IllegalArgumentException("Tipo de expressão desconhecido: " + expression.getClass().getSimpleName());
    }

    public void generateBinaryExpression(BinaryExpression binaryExpression) {
        generateExpression(binaryExpression.getLeft());
        generateExpression(binaryExpression.getRight());

        if (binaryExpression.getType() == VariableType.STRING) {
            methodContext.getMethodVisitor().visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "concat", "(Ljava/lang/String;)Ljava/lang/String;", false);
            return;
        }
        
        OperatorType operator = binaryExpression.getOperator();
        int opcode = OpcodeMapper.inferOperator(operator);

        if (operator.isRelational()) {
            generateRelationalExpression(opcode);
            return;
        }
        
        methodContext.getMethodVisitor().visitInsn(opcode);
    }

    private void generateRelationalExpression(int opcode) {
        Label trueLabel = new Label();
        Label endLabel = new Label();

        methodContext.getMethodVisitor().visitJumpInsn(opcode, trueLabel);
        methodContext.getMethodVisitor().visitInsn(Opcodes.ICONST_0);
        methodContext.getMethodVisitor().visitJumpInsn(Opcodes.GOTO, endLabel);
        methodContext.getMethodVisitor().visitLabel(trueLabel);
        methodContext.getMethodVisitor().visitInsn(Opcodes.ICONST_1);
        methodContext.getMethodVisitor().visitLabel(endLabel);
    }

    // * Só funciona pra operador NOT 
    public void generateUnaryExpression(UnaryExpression unaryExpression) {
        generateExpression(unaryExpression.getExpression());

        methodContext.getMethodVisitor().visitInsn(Opcodes.ICONST_1);
        methodContext.getMethodVisitor().visitInsn(Opcodes.IXOR);
    }

    public void generateFunctionCallExpression(FunctionCallExpression functionCallExpression) {
        this.generateFunctionCall(functionCallExpression);
    }

    public void generateFunctionCall(IFunctionCall functionCall) {
        Identifier identifier = functionCall.getIdentifier();
        String ownerName = methodContext.getClassName();
        String name = identifier.getName();
        String returnTypeDescriptor = methodContext.getVariableSymbolTable().getGlobalVariableDescriptor(identifier);
        String jvmDescriptor = DescriptorMapper.inferFunctionCall(functionCall, returnTypeDescriptor);

        functionCall.getArguments().getExpressions().forEach(this::generateExpression);
        methodContext.getMethodVisitor().visitMethodInsn(Opcodes.INVOKESTATIC, ownerName, name, jvmDescriptor, false);
    }

    public void generateIdentifier(Identifier identifier) {
        VariableManager.load(methodContext, identifier);
    }

    public void generateLiteral(Literal<?> literal) {
        if (literal instanceof LiteralBoolean)  generateLiteralBoolean((LiteralBoolean) literal);
        else if (literal instanceof LiteralInteger) generateLiteralInteger((LiteralInteger) literal);
        else if (literal instanceof LiteralString) generateLiteralString((LiteralString) literal);
        else throw new IllegalArgumentException("Tipo de literal desconhecido: " + literal.getClass().getSimpleName());
    }

    public void generateLiteralBoolean(LiteralBoolean literalBoolean) {
        methodContext.getMethodVisitor().visitInsn(literalBoolean.getValue() ? Opcodes.ICONST_1 : Opcodes.ICONST_0);
    }

    public void generateLiteralInteger(LiteralInteger literalInteger) {
        methodContext.getMethodVisitor().visitLdcInsn(literalInteger.getValue());
    }

    public void generateLiteralString(LiteralString literalString) {
        methodContext.getMethodVisitor().visitLdcInsn(literalString.getValue());
    }

}
