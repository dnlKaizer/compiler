package br.com.comcet.backend.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import br.com.comcet.backend.mapper.DescriptorMapper;
import br.com.comcet.frontend.ast.declaration.VariableDeclaration;
import br.com.comcet.frontend.ast.expression.Identifier;

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

    public void addParameters(List<VariableDeclaration> parameters) {
        parameters.forEach(parameter -> {
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
