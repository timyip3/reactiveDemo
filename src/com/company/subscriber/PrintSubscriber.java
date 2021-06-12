package com.company.subscriber;

import java.util.concurrent.Flow;

public class PrintSubscriber implements Flow.Subscriber<Integer> {
    private Flow.Subscription subscription;
    private final String name;

    public PrintSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("PrintSubscriber " + name + " received item: " + item);
//        if (item > 5)
//            throw new RuntimeException("item > 5");

        subscription.request(10);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("PrintSubscriber " + name + ": Error occurred: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("PrintSubscriber " + name + " is completed");
    }
}
