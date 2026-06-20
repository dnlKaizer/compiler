package br.com.comcet.tp7.manager;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import br.com.comcet.tp1.ast.declaration.SubroutineDeclaration;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp7.code.CommandCodeGen;
import br.com.comcet.tp7.code.DeclarationCodeGen;
import br.com.comcet.tp7.context.CodeGenContext;
import br.com.comcet.tp7.context.MethodContext;
import br.com.comcet.tp7.context.VariableSymbolTable;
import br.com.comcet.tp7.mapper.DescriptorMapper;
import br.com.comcet.tp7.mapper.OpcodeMapper;

public class SubroutineManager {

    private static final int OPCODE = Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC; 

    public static void declareSubroutine(CodeGenContext codeGenContext, SubroutineDeclaration subroutineDeclaration) {
        ClassWriter classWriter = codeGenContext.getClassWriter();
        VariableSymbolTable variableSymbolTable = codeGenContext.getVariableSymbolTable();
        Identifier id = subroutineDeclaration.getIdentifier();
        
        variableSymbolTable.enterScope();
        
        variableSymbolTable.addGlobalVariable(id);
        VariableManager.declareParameters(codeGenContext, subroutineDeclaration.getParameters());
        
        MethodVisitor methodVisitor = start(classWriter, subroutineDeclaration);
        methodVisitor.visitCode();
        
        MethodContext subroutineContext = new MethodContext(codeGenContext, methodVisitor);

        VariableType returnType = subroutineDeclaration.getReturnType();
        if (returnType != VariableType.VOID) VariableManager.declareLocal(subroutineContext, id);
        
        DeclarationCodeGen declarationCodeGen = new DeclarationCodeGen(subroutineContext);
        declarationCodeGen.generateLocalVariableDeclarations(subroutineDeclaration.getLocalVariables());

        CommandCodeGen commandCodeGen = new CommandCodeGen(subroutineContext);
        commandCodeGen.generateCommand(subroutineDeclaration.getBody());

        if (returnType != VariableType.VOID) VariableManager.load(subroutineContext, id);

        variableSymbolTable.exitScope();
        end(methodVisitor, OpcodeMapper.inferReturn(returnType));
    }
    
    public static MethodVisitor start(ClassWriter classWriter, SubroutineDeclaration subroutineDeclaration) {
        String name = subroutineDeclaration.getIdentifier().getName();
        String descriptor = DescriptorMapper.inferSubroutine(subroutineDeclaration);

        return classWriter.visitMethod(OPCODE, name, descriptor, null, null);
    }
    
    public static void end(MethodVisitor methodVisitor, int returnOpcode) {
        methodVisitor.visitInsn(returnOpcode);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
    }

    public static MethodVisitor startMain(ClassWriter classWriter) {
        String name = "main";
        String descriptor = "([Ljava/lang/String;)V";

        return classWriter.visitMethod(OPCODE, name, descriptor, null, null);
    }

    public static void endMain(MethodVisitor main) {
        end(main, Opcodes.RETURN);
    }

}
