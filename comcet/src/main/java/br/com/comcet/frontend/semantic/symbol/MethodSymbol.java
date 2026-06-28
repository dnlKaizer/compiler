package br.com.comcet.frontend.semantic.symbol;

import java.util.List;
import java.util.Objects;

import br.com.comcet.frontend.type.VariableType;

public class MethodSymbol implements ISymbol {
    private final String name;
    private final VariableType returnType;
    private final List<VariableType> parameters;

    public MethodSymbol(String name, VariableType returnType, List<VariableType> parameters) {
        Objects.requireNonNull(name, "Nome do método não pode ser nulo.");
        Objects.requireNonNull(parameters, "Lista de parâmetros não pode ser nula.");
        parameters.forEach(Objects::requireNonNull); // Garantir que nenhum parâmetro seja nulo
        Objects.requireNonNull(returnType, "Tipo de retorno não pode ser nulo.");
        
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    @Override
    public String getName() {
        return name;
    }

    public VariableType getType() {
        return returnType;
    }

    public List<VariableType> getParameters() {
        return parameters;
    }

}
