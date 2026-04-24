package br.com.comcet.tp1.ast.symbol;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.comcet.tp1.ast.type.VariableType;
import br.com.comcet.tp1.symbol.Symbol;
import br.com.comcet.tp1.symbol.SymbolTable;

public class SymbolTableTest {
    @Test
    void testAddGet() {
        String test = "x";
        Symbol symbol = new Symbol(test, VariableType.BOOLEAN, null);
        
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.addSymbol(symbol);

        assertSame(symbol, symbolTable.getSymbol("x"));
        assertThrows(IllegalArgumentException.class, () -> symbolTable.getSymbol("y"));
    }

    @Test
    void testDuplicate() {
        String test = "x";
        Symbol symbol = new Symbol(test, VariableType.BOOLEAN, null);
        
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.addSymbol(symbol);

        Symbol symbolCopy = new Symbol(test, VariableType.BOOLEAN, null);
        assertThrows(IllegalArgumentException.class, () -> {
            symbolTable.addSymbol(symbolCopy);
        });
    }
}
