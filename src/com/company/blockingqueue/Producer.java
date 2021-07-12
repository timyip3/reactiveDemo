package com.company.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

/**
 * @author timothyyip
 */
public class Producer implements Runnable {
    private BlockingQueue<Integer> blockingQueue;

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }


    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            IntStream.range(0, 10).forEach((j) -> {
                System.out.println("producer: insert = " + j);
                blockingQueue.add(j);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
