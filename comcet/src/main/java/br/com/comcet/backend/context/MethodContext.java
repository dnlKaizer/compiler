package br.com.comcet.backend.context;

import org.objectweb.asm.MethodVisitor;

public class MethodContext extends CodeGenContext {
    private final MethodVisitor methodVisitor;

    public MethodContext(CodeGenContext context, MethodVisitor methodVisitor) {
        super(context);
        this.methodVisitor = methodVisitor;
    }

    public MethodVisitor getMethodVisitor() { return methodVisitor; }
    
}
