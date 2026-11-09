package br.com.comcet.frontend.ast.command;

public interface CommandVisitor {
    void visitAssignmentCommand(AssignmentCommand assignmentCommand);
    void visitBlockCommand(BlockCommand blockCommand);
    void visitCallCommand(CallCommand callCommand);
    void visitIfCommand(IfCommand ifCommand);
    void visitReadCommand(ReadCommand readCommand);
    void visitRepeatCommand(RepeatCommand repeatCommand);
    void visitWhileCommand(WhileCommand whileCommand);
    void visitWriteCommand(WriteCommand writeCommand);
}
