package br.com.comcet.tp7.context;

import org.objectweb.asm.ClassWriter;

public class CodeGenContext {
    private final ClassWriter classWriter;
    private final VariableSymbolTable variableSymbolTable;
    private final String className;

    public CodeGenContext(ClassWriter classWriter, VariableSymbolTable variableSymbolTable, String className) {
        this.classWriter = classWriter;
        this.variableSymbolTable = variableSymbolTable;
        this.className = className;
    }

    public CodeGenContext(CodeGenContext context) {
        this(context.getClassWriter(), context.getVariableSymbolTable(), context.getClassName());
    }

    public ClassWriter getClassWriter() { return classWriter; }
    public VariableSymbolTable getVariableSymbolTable() { return variableSymbolTable; }
    public String getClassName() { return className; }
}
