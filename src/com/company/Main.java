package com.company;

import com.company.publisher.OneShotPublisher;
import com.company.subscriber.BoundRequestSizeSubscriber;

import java.util.concurrent.Flow.*;

public class Main {

    public static void main(String[] args) {
	    Publisher<Integer> publisher = new OneShotPublisher();
        Subscriber<Integer> subscriber = new BoundRequestSizeSubscriber<>(10);
        publisher.subscribe(subscriber);
    }
}
