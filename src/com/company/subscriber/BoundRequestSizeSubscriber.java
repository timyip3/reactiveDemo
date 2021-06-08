package com.company.subscriber;

import java.util.concurrent.Flow.*;
import java.util.function.Consumer;

public class BoundRequestSizeSubscriber<T> implements Subscriber<T> {
    Subscription subscription;
    final long bufferSize;
    long count;

    public BoundRequestSizeSubscriber(long bufferSize) {
        this.bufferSize = bufferSize;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("BoundRequestSizeSubscriber: onSubscribe");
        long initialRequestSize = bufferSize;
        count = bufferSize - bufferSize / 2; // re-request when half consumed
        (this.subscription = subscription).request(initialRequestSize);
    }

    @Override
    public void onNext(T item) {
        System.out.println("Subscriber: onNext: " + item.toString());
        if (--count <= 0)
            subscription.request(count = bufferSize - bufferSize / 2);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {

    }
}
