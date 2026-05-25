package br.com.comcet.tp5.symbol;

import org.junit.jupiter.api.Test;
import br.com.comcet.tp1.ast.type.VariableType;

import static org.junit.jupiter.api.Assertions.*;

public class SymbolTableTest {

    @Test
    public void testEnterAndExitScope() {
        SymbolTable table = new SymbolTable();
        VariableSymbol sym1 = new VariableSymbol("x", VariableType.INTEGER);
        
        assertTrue(table.add(sym1), "Deveria adicionar no escopo global");
        assertEquals(sym1, table.get("x"), "Deveria encontrar a variável no escopo global");
        
        table.enterScope();
        VariableSymbol sym2 = new VariableSymbol("x", VariableType.BOOLEAN); // shadowing
        assertTrue(table.add(sym2), "Deveria adicionar no escopo local (shadowing)");
        assertEquals(sym2, table.get("x"), "Deveria encontrar a variável local");
        
        table.exitScope();
        assertEquals(sym1, table.get("x"), "Deveria voltar para a variável global");
    }

    @Test
    public void testExitGlobalScopeThrowsException() {
        SymbolTable table = new SymbolTable();
        assertThrows(IllegalStateException.class, () -> {
            table.exitScope();
        });
    }

    @Test
    public void testDuplicateSymbolInSameScope() {
        SymbolTable table = new SymbolTable();
        assertTrue(table.add(new VariableSymbol("x", VariableType.INTEGER)));
        assertFalse(table.add(new VariableSymbol("x", VariableType.BOOLEAN)), "Deveria retornar false para símbolo duplicado no mesmo escopo");
    }
}
