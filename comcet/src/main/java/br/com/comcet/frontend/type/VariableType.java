package br.com.comcet.frontend.type;

import java.util.HashMap;

public enum VariableType {
    STRING("string"), INTEGER("integer"), BOOLEAN("boolean"),
    VOID("void"), ERROR("error");

    private static final HashMap<String, VariableType> BY_STRING = new HashMap<>();
    static {
        for (VariableType vt : values()) {
            BY_STRING.put(vt.name, vt);
        }
    }

    private final String name;

    private VariableType(String name) {
        this.name = name;
    }

    public static VariableType fromString(String str) {
        return BY_STRING.get(str.toLowerCase());
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
