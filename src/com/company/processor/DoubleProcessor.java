package com.company.processor;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Function;

public class DoubleProcessor extends SubmissionPublisher<Integer> implements Flow.Subscriber<Integer> {
    private Flow.Subscription subscription;
    final Function<? super Integer, ? extends Integer> function;

    public DoubleProcessor(Function<? super Integer, ? extends Integer> function) {
        this.function = function;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("DoubleProcessor onNext, item: " + item);
        subscription.request(1000);
        submit(function.apply(item));
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
