package br.com.comcet.frontend.semantic;

import br.com.comcet.frontend.exception.SemanticException;
import br.com.comcet.frontend.exception.SemanticExceptionQueue;
import br.com.comcet.frontend.semantic.symbol.ISymbol;
import br.com.comcet.frontend.semantic.symbol.SymbolTable;
import br.com.comcet.frontend.type.VariableType;

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
