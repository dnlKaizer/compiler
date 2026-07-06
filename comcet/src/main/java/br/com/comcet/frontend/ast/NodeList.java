package br.com.comcet.frontend.ast;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NodeList<T extends AstNode> extends AstNode {
    private final List<T> items;

    public NodeList(int line, int column, List<T> items) {
        super(line, column);
        Objects.requireNonNull(items, "Lista não pode ser nula.");
        items.forEach(item -> Objects.requireNonNull(item, "Item da lista não pode ser nulo."));
        this.items = items;
    }

    public List<T> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
    
}
