package com.company;

import com.company.processor.DoubleProcessor;
import com.company.subscriber.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Flow.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        withProcessor();
        onePublisherWithMultipleSubscriber();
//        completableFuture();
    }

    private static void completableFuture() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task-1");
        }).thenRunAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task-2");
        });

        System.out.println("main thread");
        completableFuture.get();
    }

    private static void onePublisherWithMultipleSubscriber() throws InterruptedException, ExecutionException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        Subscriber<Integer> firstSubscriber = new PrintSubscriber("subscriber-1");
        Subscriber<Integer> secondSubscriber = new PrintSubscriber("subscriber-2");

        publisher.subscribe(firstSubscriber);
//        publisher.subscribe(secondSubscriber);
        System.out.println("max. capacity: " + publisher.getMaxBufferCapacity());

//        Thread.sleep(1000);
        AtomicInteger sum = new AtomicInteger();
          CompletableFuture<Void> cf = publisher.consume(sum::getAndAdd);
        IntStream.range(0, 100).forEach(publisher::submit);
        Thread.sleep(1000);
//        publisher.close();
        System.out.println("sum: " + sum.get());
        //send error signal to subscribers
        if (sum.get() > 10000)
            publisher.closeExceptionally(new RuntimeException("close Exceptionally"));
//        Thread.sleep(1000);
        publisher.close();
    }

    private static void withProcessor() throws InterruptedException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        DoubleProcessor doubleProcessor = new DoubleProcessor();
        Subscriber<Integer> subscriber = new PrintSubscriber("subscriber-1");

        publisher.subscribe(doubleProcessor);
        doubleProcessor.subscribe(subscriber);

        IntStream.range(0, 1000).forEach(publisher::submit);
        Thread.sleep(1000);
        publisher.close();
    }
}
