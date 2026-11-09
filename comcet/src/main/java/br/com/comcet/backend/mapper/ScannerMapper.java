package br.com.comcet.backend.mapper;

import br.com.comcet.frontend.type.VariableType;

public class ScannerMapper {
    public static String inferParseOwner(VariableType type) {
        return switch (type) {
            case STRING -> null;
            case INTEGER -> "java/lang/Integer";
            case BOOLEAN -> "java/lang/Boolean";
            case VOID, ERROR -> throw new IllegalArgumentException("Tipo de variável inválido para parse: " + type);
        };
    }

    public static String inferParseMethod(VariableType type) {
        return switch (type) {
            case STRING -> null;
            case INTEGER -> "parseInt";
            case BOOLEAN -> "parseBoolean";
            case VOID, ERROR -> throw new IllegalArgumentException("Tipo de variável inválido para parse: " + type);
        };
    }

    public static String inferParseDescriptor(VariableType type) {
        return switch (type) {
            case STRING -> null;
            case INTEGER -> "(Ljava/lang/String;)I";
            case BOOLEAN -> "(Ljava/lang/String;)Z";
            case VOID, ERROR -> throw new IllegalArgumentException("Tipo de variável inválido para parse: " + type);
        };
    }
}
