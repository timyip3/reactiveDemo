package com.company.publisher;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class OneShotPublisher implements Publisher<Integer> {
    private final ExecutorService executor = ForkJoinPool.commonPool(); // daemon-based

    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {
        subscriber.onSubscribe(new OneShotSubscription(subscriber, executor));
    }

    static class OneShotSubscription implements Subscription {
        private final Subscriber<? super Integer> subscriber;
        private final ExecutorService executor;
        private Future<?> future; // to allow cancellation
        private boolean completed;
        private AtomicInteger value;

        OneShotSubscription(Subscriber<? super Integer> subscriber, ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;

            value = new AtomicInteger();
        }

        @Override
        public synchronized void request(long n) {
            System.out.println("OneShotSubscription: subscription being requested");

            if (n != 0 && !completed) {
                completed = true;
                if (n < 0) {
                    IllegalArgumentException ex = new IllegalArgumentException();
                    executor.execute(() -> subscriber.onError(ex));
                } else {
                    future = executor.submit(() -> {
                        try {
                            publishItems(10);
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    });

                    try {
                        future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void publishItems(long n) throws ExecutionException, InterruptedException {
            for (int i = 0; i < n; i++) {

                executor.submit(() -> {
                    int v = value.incrementAndGet();
                    System.out.println("publish item: [" + v + "] ...");
                    subscriber.onNext(v);
                }).get();
            }
        }

        @Override
        public void cancel() {
            completed = true;
            if (future != null) future.cancel(false);
        }
    }
}
