package br.com.comcet.frontend.semantic.symbol;

import br.com.comcet.frontend.type.VariableType;

public class VariableSymbol implements ISymbol {
    private final String name;
    private final VariableType type;
    
    private boolean isInitialized;

    public VariableSymbol(String name, VariableType type) {
        this.name = name;
        this.type = type;
        this.isInitialized = false;
    }

    @Override
    public String getName() {
        return name;
    }

    public VariableType getType() {
        return type;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void initialize() {
        isInitialized = true;
    }
    
}
