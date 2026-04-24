package br.com.comcet.tp3.exception;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExceptionQueue {
    private final static Queue<CompilerException> queue = new ConcurrentLinkedQueue<>();

    public static void add(CompilerException exception) {
        queue.add(exception);
    }

    public static CompilerException poll() {
        return queue.poll();
    }
    
}
