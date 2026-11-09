package br.com.comcet.frontend.exception;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public final class AntlrErrorCollector extends BaseErrorListener {
    private final List<CompilerException> errors = new ArrayList<>();
    private final boolean isLexical;

    public AntlrErrorCollector(boolean isLexical) {
        this.isLexical = isLexical;
    }

    @Override
    public void syntaxError(
        Recognizer<?, ?> recognizer,
        Object offendingSymbol,
        int line,
        int charPositionInLine,
        String msg,
        RecognitionException e
    ) {
        if (isLexical) {
            errors.add(new LexicalException(msg, line, charPositionInLine));
        } else {
            errors.add(new SyntaticException(msg, line, charPositionInLine));
        }
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<CompilerException> getErrors() {
        return errors;
    }

    public String getErrorMessages() {
        StringBuilder sb = new StringBuilder();
        for (CompilerException error : errors) {
            sb.append(error.getMessage()).append(System.lineSeparator());
        }
        return sb.toString();
    }

}