package com.company.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

/**
 * @author timothyyip
 */
public class Producer implements Runnable {
    private BlockingQueue<Integer> blockingQueue;
    private String name;

    public Producer(BlockingQueue<Integer> blockingQueue, String name) {
        this.blockingQueue = blockingQueue;
        this.name = name;
    }


    @Override
    public void run() {
        IntStream.range(0, 10).forEach((j) -> {
            System.out.printf("producer: %s insert = %s%n", name,  j);
            blockingQueue.add(j);

//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
        });
    }
}
