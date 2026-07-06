package br.com.comcet.frontend.ast.command;

import java.util.List;
import java.util.Objects;

import br.com.comcet.frontend.ast.NodeList;

public class BlockCommand extends Command {
    private final NodeList<Command> commands;

    public BlockCommand(int line, int column, NodeList<Command> commands) {
        super(line, column);

        Objects.requireNonNull(commands, "Lista de comandos não pode ser nula.");
        commands.getItems().forEach(command -> Objects.requireNonNull(command, "Comando na lista não pode ser nulo."));

        this.commands = commands;
    }

    // Construtor para testes
    public BlockCommand(NodeList<Command> commands) {
        this(1, 1, commands);
    }

    public List<Command> getCommands() {
        return commands.getItems();
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitBlockCommand(this);
    }

    @Override
    public String toString() {
        String br = IndentationUtil.NEWLINE;

        StringBuilder sb = new StringBuilder();
        sb.append("begin").append(br);
        commands.getItems().forEach(command -> sb.append(IndentationUtil.indentCommand(command)).append(";").append(br));
        sb.append("end");

        return sb.toString();
    }

}
