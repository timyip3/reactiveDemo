package com.company;

import com.company.processor.DoubleProcessor;
import com.company.subscriber.*;

import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //withProcessor();
        onePublisherWithMultipleSubscriber();
    }

    private static void onePublisherWithMultipleSubscriber() throws InterruptedException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        Subscriber<Integer> firstSubscriber = new PrintSubscriber("subscriber-1");
        Subscriber<Integer> secondSubscriber = new PrintSubscriber("subscriber-2");

        publisher.subscribe(firstSubscriber);
        publisher.subscribe(secondSubscriber);

        IntStream.range(0, 1000).forEach(publisher::submit);
        Thread.sleep(1000);
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
