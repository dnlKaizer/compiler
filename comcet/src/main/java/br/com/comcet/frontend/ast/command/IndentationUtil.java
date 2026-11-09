package br.com.comcet.frontend.ast.command;

public final class IndentationUtil {

    private IndentationUtil() {
        throw new UnsupportedOperationException("Classe utilitária não pode ser instanciada.");
    }

    public final static String TAB = "   ";
    public final static String NEWLINE = System.getProperty("line.separator", "\n");

    public static String indentCommand(Command command) {
        return indent(command.toString());
    }

    public static String indent(String str) {
        String[] lines = str.split(NEWLINE);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            if (i > 0) sb.append(NEWLINE);
            sb.append(TAB).append(lines[i]);
        }

        return sb.toString();
    }
}
