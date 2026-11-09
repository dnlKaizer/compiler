package br.com.comcet.frontend.exception;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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

    public String getErrorMessages() {
        StringBuilder sb = new StringBuilder();
        for (SemanticException error : queue) {
            sb.append(error.getMessage()).append(System.lineSeparator());
        }
        return sb.toString();
    }

}
