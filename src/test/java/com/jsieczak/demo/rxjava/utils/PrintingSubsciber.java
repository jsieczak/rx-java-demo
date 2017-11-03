package com.jsieczak.demo.rxjava.utils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.LoggerFactory;

public class PrintingSubsciber implements Subscriber {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PrintingSubsciber.class);

    @Override
    public void onSubscribe(Subscription subscription) {
        LOG.info("onSubscribe called");
    }

    @Override
    public void onNext(Object o) {
        LOG.info("onNext called with object : {}", o.toString());
    }

    @Override
    public void onError(Throwable throwable) {
        LOG.info("onError called");
    }

    @Override
    public void onComplete() {

    }
}
