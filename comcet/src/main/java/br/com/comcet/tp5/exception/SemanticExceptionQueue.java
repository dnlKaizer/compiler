package br.com.comcet.tp5.exception;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import br.com.comcet.tp3.exception.SemanticException;

public class SemanticExceptionQueue {
    private final Queue<SemanticException> queue;

    public SemanticExceptionQueue() {
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void add(SemanticException exception) {
        queue.add(exception);
    }

    public SemanticException poll() {
        if (!queue.isEmpty()) {
            return queue.poll();
        }
        return null;
    }

}
