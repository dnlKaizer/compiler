package br.com.comcet.backend.bytecode;

import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import br.com.comcet.backend.context.CodeGenContext;
import br.com.comcet.backend.context.MethodContext;
import br.com.comcet.backend.context.VariableSymbolTable;
import br.com.comcet.backend.manager.ScannerManager;
import br.com.comcet.backend.manager.SubroutineManager;
import br.com.comcet.frontend.ast.program.Program;

public class CodeGen {

    private final Program program;
    private final ClassWriter classWriter;
    private final VariableSymbolTable variableSymbolTable;
    private final String className;

    public CodeGen(Program program) {
        this.program = program;
        this.classWriter = initClassWriter();
        this.variableSymbolTable = new VariableSymbolTable();
        this.className = program.getName();

        execute();
    }

    private void execute() {
        CodeGenContext codeGenContext = new CodeGenContext(classWriter, variableSymbolTable, className);

        DeclarationCodeGen declarationCodeGen = new DeclarationCodeGen(codeGenContext);
        declarationCodeGen.generateGlobalVariableDeclarations(program.getDeclarations());
        declarationCodeGen.generateSubroutineDeclarations(program.getSubroutineDeclarations());

        MethodVisitor main = SubroutineManager.startMain(classWriter);

        MethodContext mainContext = new MethodContext(codeGenContext, main);
        ScannerManager.instantiateScanner(mainContext);

        CommandCodeGen commandCodeGen = new CommandCodeGen(mainContext);
        commandCodeGen.generateCommand(program.getBody());
        
        SubroutineManager.endMain(main);
    }

    private ClassWriter initClassWriter() {
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        classWriter.visit(Opcodes.V11, Opcodes.ACC_PUBLIC, program.getName(), null, "java/lang/Object", null);
        return classWriter;
    }

    public void save() {
        try (FileOutputStream fos = new FileOutputStream(program.getName() + ".class")) {
            fos.write(classWriter.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}