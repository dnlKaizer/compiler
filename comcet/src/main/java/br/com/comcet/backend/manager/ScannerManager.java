package br.com.comcet.backend.manager;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import br.com.comcet.backend.context.MethodContext;
import br.com.comcet.backend.mapper.ScannerMapper;
import br.com.comcet.frontend.type.VariableType;

public class ScannerManager {

    public static void instantiateScanner(MethodContext methodContext) {
        MethodVisitor main = methodContext.getMethodVisitor();
        ClassWriter classWriter = methodContext.getClassWriter();
        String className = methodContext.getClassName();

        int opcode = Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC;
        classWriter.visitField(opcode, "$scanner", "Ljava/util/Scanner;", null, null).visitEnd();
        main.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
        main.visitInsn(Opcodes.DUP);
        main.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;");
        main.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false);
        main.visitFieldInsn(Opcodes.PUTSTATIC, className, "$scanner", "Ljava/util/Scanner;");
    }

    public static void read(MethodContext methodContext, VariableType expectedType) {
        MethodVisitor methodVisitor = methodContext.getMethodVisitor();
        String className = methodContext.getClassName();

        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, className, "$scanner", "Ljava/util/Scanner;");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "next", "()Ljava/lang/String;", false);

        if (expectedType != VariableType.STRING) {
            String owner = ScannerMapper.inferParseOwner(expectedType);
            String method = ScannerMapper.inferParseMethod(expectedType);
            String descriptor = ScannerMapper.inferParseDescriptor(expectedType);
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, owner, method, descriptor, false);
        }
    }

    public static void skipLine(MethodContext methodContext) {
        MethodVisitor methodVisitor = methodContext.getMethodVisitor();
        String className = methodContext.getClassName();

        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, className, "$scanner", "Ljava/util/Scanner;");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextLine", "()Ljava/lang/String;", false);
        methodVisitor.visitInsn(Opcodes.POP);
    }

}
