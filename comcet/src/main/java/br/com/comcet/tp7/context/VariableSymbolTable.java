package br.com.comcet.tp7.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import br.com.comcet.tp1.ast.declaration.ParametersDeclaration;
import br.com.comcet.tp1.ast.expression.Identifier;
import br.com.comcet.tp7.mapper.DescriptorMapper;

public class VariableSymbolTable {

    private final Map<String, String> globalVariableDescriptorMap;
    private final Stack<Map<String, Integer>> localVariableScopes;

    private int indexCounter = 0;

    public VariableSymbolTable() {
        this.localVariableScopes = new Stack<>();
        this.globalVariableDescriptorMap = new HashMap<>();

        enterScope();
    }
    
    public void addGlobalVariable(Identifier identifier) {
        String name = identifier.getName();
        String type = DescriptorMapper.inferType(identifier.getType());

        globalVariableDescriptorMap.put(name, type);
    }

    public String getGlobalVariableDescriptor(Identifier identifier) {
        return globalVariableDescriptorMap.get(identifier.getName());
    }

    public void enterScope() {
        indexCounter = 0;
        localVariableScopes.push(new HashMap<>());
    }

    public void exitScope() {
        localVariableScopes.pop();
    }

    public void addParameters(ParametersDeclaration parameters) {
        parameters.getVariableDeclarations().forEach(parameter -> {
            parameter.getIdentifiers().forEach(this::addLocalVariable);
        });
    }

    private Map<String, Integer> current() {
        return localVariableScopes.get(localVariableScopes.size() - 1);
    }

    public int getLocalVariableIndex(Identifier identifier) {
        return current().get(identifier.getName());
    }

    public void addLocalVariable(Identifier identifier) {
        current().put(identifier.getName(), indexCounter++);
    }

    public boolean isLocalVariable(Identifier identifier) {
        return current().containsKey(identifier.getName());
    }

}
