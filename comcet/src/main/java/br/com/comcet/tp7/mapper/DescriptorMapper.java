package br.com.comcet.tp7.mapper;

import br.com.comcet.tp1.ast.declaration.SubroutineDeclaration;
import br.com.comcet.tp1.ast.interfaces.IFunctionCall;
import br.com.comcet.tp1.ast.type.VariableType;

public class DescriptorMapper {
    public static String inferType(VariableType type) {
        return switch (type) {
            case STRING -> "Ljava/lang/String;";
            case INTEGER -> "I";
            case BOOLEAN -> "Z";
            case VOID -> "V";
            case ERROR -> throw new IllegalArgumentException("Tipo de variável inválido: " + type);
        };
    }

    public static String inferSubroutine(SubroutineDeclaration subroutineDeclaration) {
        StringBuilder descriptor = new StringBuilder("(");
        subroutineDeclaration.getParameters().getVariableDeclarations().forEach(variableDeclaration -> {
            String typeDescriptor = inferType(variableDeclaration.getType());
            variableDeclaration.getIdentifiers().forEach(id -> descriptor.append(typeDescriptor));
        }); 
        descriptor.append(")").append(inferType(subroutineDeclaration.getReturnType()));
        return descriptor.toString();
    }

    public static String inferFunctionCall(IFunctionCall functionCall, String returnTypeDescriptor) {
        StringBuilder descriptor = new StringBuilder("(");
        functionCall.getArguments().getExpressions().forEach(expression -> descriptor.append(inferType(expression.getType())));
        descriptor.append(")").append(returnTypeDescriptor);
        return descriptor.toString();
    }
}
