package br.com.comcet.tp1.symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Symbol> symbols;

    public SymbolTable() {
        this.symbols = new HashMap<>();
    }

    public void addSymbol(Symbol symbol) {
        String name = symbol.getName();
        if (symbols.containsKey(name)) {
            throw new IllegalArgumentException("Símbolo '" + name + "' já declarado.");
        }
        symbols.put(name, symbol);
    }

    public Symbol getSymbol(String name) {
        Symbol symbol = symbols.get(name);
        if (symbol == null) {
            throw new IllegalArgumentException("Símbolo '" + name + "' não declarado.");
        }
        return symbol;
    }
}
