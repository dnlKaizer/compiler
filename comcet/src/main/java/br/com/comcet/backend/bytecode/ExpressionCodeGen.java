package br.com.comcet.backend.bytecode;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

import br.com.comcet.backend.context.MethodContext;
import br.com.comcet.backend.manager.VariableManager;
import br.com.comcet.backend.mapper.DescriptorMapper;
import br.com.comcet.backend.mapper.OpcodeMapper;
import br.com.comcet.frontend.ast.expression.*;
import br.com.comcet.frontend.ast.interfaces.IFunctionCall;
import br.com.comcet.frontend.type.OperatorType;
import br.com.comcet.frontend.type.VariableType;

public class ExpressionCodeGen implements ExpressionVisitor<Void> {

    private final MethodContext methodContext;

    public ExpressionCodeGen(MethodContext methodContext) {
        this.methodContext = methodContext;
    }

    @Override
    public Void visitBinaryExpression(BinaryExpression binaryExpression) {
        binaryExpression.getLeft().accept(this);
        binaryExpression.getRight().accept(this);

        if (binaryExpression.getType() == VariableType.STRING) {
            methodContext.getMethodVisitor().visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "concat", "(Ljava/lang/String;)Ljava/lang/String;", false);
            return null;
        }
        
        OperatorType operator = binaryExpression.getOperator();
        int opcode = OpcodeMapper.inferOperator(operator);

        if (operator.isRelational()) {
            visitRelationalExpression(opcode);
            return null;
        }
        
        methodContext.getMethodVisitor().visitInsn(opcode);
        return null;
    }

    private void visitRelationalExpression(int opcode) {
        Label trueLabel = new Label();
        Label endLabel = new Label();

        methodContext.getMethodVisitor().visitJumpInsn(opcode, trueLabel);
        methodContext.getMethodVisitor().visitInsn(Opcodes.ICONST_0);
        methodContext.getMethodVisitor().visitJumpInsn(Opcodes.GOTO, endLabel);
        methodContext.getMethodVisitor().visitLabel(trueLabel);
        methodContext.getMethodVisitor().visitInsn(Opcodes.ICONST_1);
        methodContext.getMethodVisitor().visitLabel(endLabel);
    }

    @Override
    // * Só funciona pra operador NOT 
    public Void visitUnaryExpression(UnaryExpression unaryExpression) {
        unaryExpression.getExpression().accept(this);

        methodContext.getMethodVisitor().visitInsn(Opcodes.ICONST_1);
        methodContext.getMethodVisitor().visitInsn(Opcodes.IXOR);
        return null;
    }

    @Override
    public Void visitFunctionCallExpression(FunctionCallExpression functionCallExpression) {
        this.visitFunctionCall(functionCallExpression);
        return null;
    }

    public void visitFunctionCall(IFunctionCall functionCall) {
        Identifier identifier = functionCall.getIdentifier();
        String ownerName = methodContext.getClassName();
        String name = identifier.getName();
        String returnTypeDescriptor = methodContext.getVariableSymbolTable().getGlobalVariableDescriptor(identifier);
        String jvmDescriptor = DescriptorMapper.inferFunctionCall(functionCall, returnTypeDescriptor);

        functionCall.getArguments().getExpressions().forEach(expression -> expression.accept(this));
        methodContext.getMethodVisitor().visitMethodInsn(Opcodes.INVOKESTATIC, ownerName, name, jvmDescriptor, false);
    }

    @Override
    public Void visitIdentifier(Identifier identifier) {
        VariableManager.load(methodContext, identifier);
        return null;
    }

    @Override
    public Void visitLiteralBoolean(LiteralBoolean literalBoolean) {
        methodContext.getMethodVisitor().visitInsn(literalBoolean.getValue() ? Opcodes.ICONST_1 : Opcodes.ICONST_0);
        return null;
    }

    @Override
    public Void visitLiteralInteger(LiteralInteger literalInteger) {
        methodContext.getMethodVisitor().visitLdcInsn(literalInteger.getValue());
        return null;
    }

    @Override
    public Void visitLiteralString(LiteralString literalString) {
        methodContext.getMethodVisitor().visitLdcInsn(literalString.getValue());
        return null;
    }

}
