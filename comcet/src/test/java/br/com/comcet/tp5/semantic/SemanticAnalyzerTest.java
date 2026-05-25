package br.com.comcet.tp5.semantic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import br.com.comcet.tp1.ast.program.Program;
import br.com.comcet.tp4.MiniPascalAntlrVisitor;
import br.com.comcet.tp4.ParseTreeCreator;
import br.com.comcet.tp5.exception.SemanticExceptionQueue;
import br.com.comcet.tp3.exception.SemanticException;
import org.antlr.v4.runtime.tree.ParseTree;

public class SemanticAnalyzerTest {

    private SemanticExceptionQueue analyze(String code) {
        ParseTree tree = ParseTreeCreator.createTree(code);
        MiniPascalAntlrVisitor visitor = new MiniPascalAntlrVisitor();
        Program program = (Program) visitor.visit(tree);
        SemanticAnalyzer analyzer = new SemanticAnalyzer(program);
        return analyzer.getExceptions();
    }

    @Test
    public void testValidProgram() {
        String code = "program Valid; \n" +
                      "var x : integer; \n" +
                      "begin \n" +
                      "  x := 10; \n" +
                      "end.";
        SemanticExceptionQueue exceptions = analyze(code);
        assertTrue(exceptions.isEmpty(), "Não deve haver erros semânticos em um programa válido");
    }

    @Test
    public void testUndeclaredVariable() {
        String code = "program Error1; \n" +
                      "begin \n" +
                      "  y := 10; \n" +
                      "end.";
        SemanticExceptionQueue exceptions = analyze(code);
        assertFalse(exceptions.isEmpty(), "Deveria retornar erro de variável não declarada");
        SemanticException ex = exceptions.poll();
        assertTrue(ex.getMessage().contains("Identificador 'y' não declarado"), "Mensagem: " + ex.getMessage());
    }

    @Test
    public void testTypeMismatchAssignment() {
        String code = "program Error2; \n" +
                      "var x : integer; \n" +
                      "begin \n" +
                      "  x := true; \n" +
                      "end.";
        SemanticExceptionQueue exceptions = analyze(code);
        assertFalse(exceptions.isEmpty(), "Deveria retornar erro de tipo incompatível");
        SemanticException ex = exceptions.poll();
        assertTrue(ex.getMessage().contains("é 'integer', mas expressão é do tipo 'boolean'"), "Mensagem: " + ex.getMessage());
    }

    @Test
    public void testFunctionMissingReturnAssignment() {
        String code = "program Error3; \n" +
                      "var discard: integer; \n" +
                      "function foo(): integer; \n" +
                      "begin \n" +
                      "  discard := 1; \n" +
                      "end; \n" +
                      "begin discard := 1; end.";
        SemanticExceptionQueue exceptions = analyze(code);
        assertFalse(exceptions.isEmpty(), "Deveria cobrar retorno da função");
        SemanticException ex = exceptions.poll();
        assertTrue(ex.getMessage().contains("Função 'foo' deve retornar um valor do tipo 'integer'"), "Mensagem: " + ex.getMessage());
    }

    @Test
    public void testCallWithWrongNumberOfArguments() {
        String code = "program Error4; \n" +
                      "var discard: integer; \n" +
                      "procedure foo(a, b: integer); begin discard := 1; end; \n" +
                      "begin \n" +
                      "  foo(10); \n" +
                      "end.";
        SemanticExceptionQueue exceptions = analyze(code);
        assertFalse(exceptions.isEmpty(), "Deveria retornar erro de aridade da função");
        SemanticException ex = exceptions.poll();
        assertTrue(ex.getMessage().contains("Número de argumentos fornecidos (1) não corresponde"), "Mensagem: " + ex.getMessage());
    }

    @Test
    public void testIfConditionType() {
        String code = "program Error5; \n" +
                      "var discard: integer; \n" +
                      "begin \n" +
                      "  if 10 then begin discard := 1; end; \n" +
                      "end.";
        SemanticExceptionQueue exceptions = analyze(code);
        assertFalse(exceptions.isEmpty(), "Deveria verificar se if é booleano");
        SemanticException ex = exceptions.poll();
        assertTrue(ex.getMessage().contains("Tipo da condição do comando 'if' deve ser 'boolean', mas é 'integer'"), "Mensagem: " + ex.getMessage());
    }

    @Test
    public void testBinaryOperatorTypeMismatch() {
        String code = "program Error6; \n" +
                      "var x : integer; \n" +
                      "begin \n" +
                      "  x := 10 + true; \n" +
                      "end.";
        SemanticExceptionQueue exceptions = analyze(code);
        assertFalse(exceptions.isEmpty(), "Deveria verificar tipo na expressão matemática");
        SemanticException ex = exceptions.poll();
        assertTrue(ex.getMessage().contains("não é compatível com os tipos 'integer' e 'boolean'"), "Mensagem: " + ex.getMessage());
    }
    
    @Test
    public void testValidFunctionReturn() {
        String code = "program ValidFunc; \n" +
                      "var discard: integer; \n" +
                      "function sum(a,b: integer): integer; \n" +
                      "begin \n" +
                      "  sum := a + b; \n" +
                      "end; \n" +
                      "begin discard := 1; end.";
        SemanticExceptionQueue exceptions = analyze(code);
        assertTrue(exceptions.isEmpty(), "Não deve acusar erro se a função retorna um valor corretamente");
    }

}
