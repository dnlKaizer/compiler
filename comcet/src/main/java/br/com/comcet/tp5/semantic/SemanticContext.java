package br.com.comcet.tp5.semantic;

import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp3.exception.SemanticException;
import br.com.comcet.tp5.exception.SemanticExceptionQueue;
import br.com.comcet.tp5.symbol.ISymbol;
import br.com.comcet.tp5.symbol.SymbolTable;

public class SemanticContext {
    private final SymbolTable symbolTable;
    private final SemanticExceptionQueue exceptionQueue;

    public SemanticContext() {
        this.symbolTable = new SymbolTable();
        this.exceptionQueue = new SemanticExceptionQueue();
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public SemanticExceptionQueue getExceptionQueue() {
        return exceptionQueue;
    }

    public void enterScope() {
        symbolTable.enterScope();
    }

    public void exitScope() {
        symbolTable.exitScope();
    }

    public void addSymbol(ISymbol symbol) {
        symbolTable.add(symbol);
    }

    public ISymbol getSymbol(String name) {
        return symbolTable.get(name);
    }

    public ISymbol getSymbolFromFatherScope(String name) {
        return symbolTable.getFromFatherScope(name);
    }

    public VariableType getSymbolType(String name) {
        ISymbol symbol = symbolTable.get(name);
        return symbol != null ? symbol.getType() : VariableType.ERROR;
    }

    public void addException(SemanticException exception) {
        exceptionQueue.add(exception);
    }

    public SemanticException pollException() {
        return exceptionQueue.poll();
    }

}
