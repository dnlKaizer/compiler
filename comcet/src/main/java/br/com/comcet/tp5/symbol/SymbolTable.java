package br.com.comcet.tp5.symbol;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class SymbolTable {
    private final Stack<Map<String, ISymbol>> scopes;

    public SymbolTable() {
        this.scopes = new Stack<>();
        this.enterScope();
    }

    public void enterScope() {
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        if (scopes.size() == 1) {
            throw new IllegalStateException("Não é possível sair do escopo global.");
        }
        scopes.pop();
    }

    public boolean add(ISymbol symbol) {
        if (scopes.peek().containsKey(symbol.getName())) {
            return false; // Símbolo já declarado no escopo atual
        }
        scopes.peek().put(symbol.getName(), symbol);
        return true;
    }

    public ISymbol get(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            ISymbol symbol = scopes.get(i).get(name);
            if (symbol != null) {
                return symbol;
            }
        }
        return null;
    }

}