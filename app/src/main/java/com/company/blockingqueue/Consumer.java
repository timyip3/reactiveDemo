package com.company.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author timothyyip
 */
public class Consumer implements Runnable {
    private BlockingQueue<Integer> blockingQueue;
    private String name;

    public Consumer(BlockingQueue<Integer> blockingQueue, String name) {
        this.blockingQueue = blockingQueue;
        this.name = name;
    }

    @Override
    public void run() {
        while (!blockingQueue.isEmpty()) {
            Integer element = blockingQueue.element();
            System.out.printf("Consumer: %s element = %s%n", name, element);
            blockingQueue.remove(element);
        }
    }
}
