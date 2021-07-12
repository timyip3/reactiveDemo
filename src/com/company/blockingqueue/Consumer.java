package com.company.blockingqueue;

import java.util.concurrent.BlockingQueue;

/**
 * @author timothyyip
 */
public class Consumer implements Runnable {
    private BlockingQueue<Integer> blockingQueue;

    public Consumer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (!blockingQueue.isEmpty()) {
            Integer element = blockingQueue.element();
            System.out.println("Consumer: element = " + element);
            blockingQueue.remove(element);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
