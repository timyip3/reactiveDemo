package com.company.processor;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class DoubleProcessor extends SubmissionPublisher<Integer> implements Flow.Subscriber<Integer> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1000);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("DoubleProcessor onNext, item: " + item);
        submit(item * 2);
        subscription.request(1000);
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println("DoubleProcessor error occurred: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("DoubleProcessor completed");
        close();
    }
}
