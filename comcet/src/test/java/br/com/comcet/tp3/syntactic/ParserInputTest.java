package br.com.comcet.tp3.syntactic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.comcet.tp1.token.TokenType;
import br.com.comcet.tp2.scanner.Lexer;
import br.com.comcet.tp2.scanner.charstream.StringCharStream;
import br.com.comcet.tp3.exception.CompilerException;
import br.com.comcet.tp3.exception.ExceptionQueue;

class ParserInputTest {

    @BeforeEach
    void clearExceptionQueue() {
        while (ExceptionQueue.poll() != null) {
            // drain global queue between tests
        }
    }

    @Test
    void matchAndAdvanceAdvancesWhenTokenMatches() {
        ParserInput input = parserInput("x := 1;");
        input.advance();

        input.matchAndAdvance(TokenType.ID);

        assertEquals(TokenType.ASSIGN, input.getCurrentTokenType());
    }

    @Test
    void matchAndAdvanceQueuesErrorWhenTokenDoesNotMatch() {
        ParserInput input = parserInput("x := 1;");
        input.advance();

        input.matchAndAdvance(TokenType.WHILE);

        assertEquals(TokenType.ID, input.getCurrentTokenType());

        CompilerException error = ExceptionQueue.poll();
        assertNotNull(error);
        assertTrue(error.getMessage().contains("Esperado 'WHILE'"));
    }

    @Test
    void skipUntilConsumesDelimiterAndStopsAfterIt() {
        ParserInput input = parserInput("x := 1; y := 2;");
        input.advance();

        input.skipUntil(List.of(TokenType.SEMICOLON));

        assertEquals(TokenType.ID, input.getCurrentTokenType());
        assertEquals("y", input.getCurrentToken().text());
    }

    private ParserInput parserInput(String code) {
        Lexer lexer = Lexer.pascal(new StringCharStream(code));
        return new ParserInput(lexer);
    }
}
