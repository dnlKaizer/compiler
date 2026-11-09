package br.com.comcet.backend.manager;

import java.util.List;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import br.com.comcet.backend.context.*;
import br.com.comcet.backend.mapper.DescriptorMapper;
import br.com.comcet.backend.mapper.OpcodeMapper;
import br.com.comcet.frontend.ast.declaration.VariableDeclaration;
import br.com.comcet.frontend.ast.expression.Identifier;

public class VariableManager {

    public static void declareGlobal(CodeGenContext codeGenContext, Identifier identifier) {
        VariableSymbolTable variableSymbolTable = codeGenContext.getVariableSymbolTable();
        ClassWriter classWriter = codeGenContext.getClassWriter();
        
        int access = Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC;
        String jvmDescriptor = DescriptorMapper.inferType(identifier.getType());

        classWriter.visitField(access, identifier.getName(), jvmDescriptor, null, null).visitEnd();

        variableSymbolTable.addGlobalVariable(identifier);
    }

    public static void declareParameters(CodeGenContext codeGenContext, List<VariableDeclaration> parameters) {
        codeGenContext.getVariableSymbolTable().addParameters(parameters);
    }

    public static void declareLocal(CodeGenContext codeGenContext, Identifier identifier) {
        codeGenContext.getVariableSymbolTable().addLocalVariable(identifier);
    }

    public static void store(MethodContext methodContext, Identifier identifier) {
        VariableSymbolTable variableSymbolTable = methodContext.getVariableSymbolTable();
        boolean isLocal = variableSymbolTable.isLocalVariable(identifier);
        int localOpcode = OpcodeMapper.inferStore(identifier.getType());

        if (isLocal) visitLocalVariable(methodContext, identifier, localOpcode);
        else visitGlobalVariable(methodContext, identifier, Opcodes.PUTSTATIC);
    }

    public static void load(MethodContext methodContext, Identifier identifier) {
        VariableSymbolTable variableSymbolTable = methodContext.getVariableSymbolTable();
        boolean isLocal = variableSymbolTable.isLocalVariable(identifier);
        int localOpcode = OpcodeMapper.inferLoad(identifier.getType());

        if (isLocal) visitLocalVariable(methodContext, identifier, localOpcode);
        else visitGlobalVariable(methodContext, identifier, Opcodes.GETSTATIC);
    }

    private static void visitLocalVariable(MethodContext methodContext, Identifier identifier, int opcode) {
        VariableSymbolTable variableSymbolTable = methodContext.getVariableSymbolTable();
        MethodVisitor methodVisitor = methodContext.getMethodVisitor();

        int index = variableSymbolTable.getLocalVariableIndex(identifier);
        methodVisitor.visitVarInsn(opcode, index);
    }

    private static void visitGlobalVariable(MethodContext methodContext, Identifier identifier, int opcode) {
        String className = methodContext.getClassName();
        MethodVisitor methodVisitor = methodContext.getMethodVisitor();
        String jvmDescriptor = DescriptorMapper.inferType(identifier.getType());

        methodVisitor.visitFieldInsn(opcode, className, identifier.getName(), jvmDescriptor);
    }

}
