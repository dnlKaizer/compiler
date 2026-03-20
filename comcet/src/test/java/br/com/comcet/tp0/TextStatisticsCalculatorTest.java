package br.com.comcet.tp0;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextStatisticsCalculatorTest {

    @Test
    public void testBasicStatistics() {
        String test = "Java e legal. Java e potente.";
        String output = TextStatisticsCalculator.calculate(test).toString();

        assertTrue(output.contains("Caracteres: 22"));
        assertTrue(output.contains("Palavras: 6"));
        assertTrue(output.contains("Frequente: e"));
    }

    @Test
    public void testDirectText() {
        String test = "Compiladores sao divertidos";
        String output = TextStatisticsCalculator.calculate(test).toString();    

        assertTrue(output.contains("Caracteres: 25"));
        assertTrue(output.contains("Frequente: o"));
        assertTrue(output.contains("Palavras: 3"));
        assertTrue(output.contains("Frequente: compiladores"));
    }

    @Test
    public void testEmptyText() {
        String test = "";
        String output = TextStatisticsCalculator.calculate(test).toString();  

        assertTrue(output.contains("Texto vazio."));
    }
}