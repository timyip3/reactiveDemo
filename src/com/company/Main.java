package com.company;

import com.company.publisher.*;
import com.company.subscriber.*;

import java.util.concurrent.Flow.*;

public class Main {

    public static void main(String[] args) {
	    Publisher<Integer> publisher = new OneShotPublisher();
        Subscriber<Integer> subscriber = new BoundRequestSizeSubscriber<Integer>(10);
        publisher.subscribe(subscriber);
    }
}
