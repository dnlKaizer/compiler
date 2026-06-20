package br.com.comcet.tp7.mapper;

import org.objectweb.asm.Opcodes;

import br.com.comcet.tp1.ast.type.OperatorType;
import br.com.comcet.tp1.ast.type.VariableType;

public class OpcodeMapper {
    public static int inferOperator(OperatorType operator) {
        return switch (operator) {
            case MULT -> Opcodes.IMUL;
            case DIV -> Opcodes.IDIV;

            case SUM -> Opcodes.IADD;
            case SUB -> Opcodes.ISUB;

            case NOT -> Opcodes.ICONST_1; // ! Não usado

            case AND -> Opcodes.IAND;
            case OR -> Opcodes.IOR;
            case XOR -> Opcodes.IXOR;
            
            case EQUAL -> Opcodes.IF_ICMPEQ;
            case NOT_EQUAL -> Opcodes.IF_ICMPNE;
            case GREATER_THAN -> Opcodes.IF_ICMPGT;
            case LESS_THAN -> Opcodes.IF_ICMPLT;
            case GREATER_EQUAL -> Opcodes.IF_ICMPGE;
            case LESS_EQUAL -> Opcodes.IF_ICMPLE;

            default -> throw new IllegalArgumentException("Tipo de operador desconhecido: " + operator);
        };
    }

    public static int inferLoad(VariableType type) {
        return switch (type) {
            case STRING -> Opcodes.ALOAD;
            case INTEGER, BOOLEAN -> Opcodes.ILOAD;
            case VOID, ERROR -> throw new IllegalArgumentException("Tipo de variável inválido para load: " + type);
        };
    }

    public static int inferStore(VariableType type) {
        return switch (type) {
            case STRING -> Opcodes.ASTORE;
            case INTEGER, BOOLEAN -> Opcodes.ISTORE;
            case VOID, ERROR -> throw new IllegalArgumentException("Tipo de variável inválido para store: " + type);
        };
    }

    public static int inferReturn(VariableType type) {
        return switch (type) {
            case STRING -> Opcodes.ARETURN;
            case INTEGER, BOOLEAN -> Opcodes.IRETURN;
            case VOID -> Opcodes.RETURN;
            case ERROR -> throw new IllegalArgumentException("Tipo de variável inválido para return: " + type);
        };
    }
}
