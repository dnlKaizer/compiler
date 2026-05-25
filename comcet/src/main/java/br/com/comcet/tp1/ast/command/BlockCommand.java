package br.com.comcet.tp1.ast.command;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BlockCommand extends Command {
    private final List<Command> commands;

    public BlockCommand(int line, int column, List<Command> commands) {
        super(line, column);

        Objects.requireNonNull(commands, "Lista de comandos não pode ser nula.");
        commands.forEach(command -> Objects.requireNonNull(command, "Comando na lista não pode ser nulo."));

        this.commands = commands;
    }

    // Construtor para testes
    public BlockCommand(List<Command> commands) {
        this(1, 1, commands);
    }

    public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;

        StringBuilder sb = new StringBuilder();
        sb.append("begin").append(br);
        for (int i = 0; i < commands.size(); i++) {
            sb.append(IndentationUtil.indentCommand(commands.get(i))).append(";").append(br);
        }
        sb.append("end");

        return sb.toString();
    }

}
