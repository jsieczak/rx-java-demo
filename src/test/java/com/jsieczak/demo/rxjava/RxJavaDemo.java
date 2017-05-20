package com.jsieczak.demo.rxjava;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;


public class RxJavaDemo {

    private static final Logger log = LoggerFactory.getLogger(RxJavaDemo.class);

    @Test
    public void name() throws Exception {
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("jeden");
            emitter.onNext("dwa");
            emitter.onComplete();
        });

    }


    @Test
    public void shouldCreateObservableAndSubscribeToIt() throws Exception {
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("jeden");
            emitter.onNext("dwa");
            emitter.onComplete();
        });

        Disposable d = observable.subscribe(
                s -> log.info("onNext"),
                error -> {
                    log.info("onError");
                    error.printStackTrace();
                },
                () -> log.info("onComplete"),
                disposable -> log.info("onSubscribe")
        );
    }

    @Test
    public void shouldCreateSimpleObservable() throws Exception {
        Observable<String> obs = Observable.create(emitter -> {
            log.info("Create begin");

            log.info("emitting jeden");
            emitter.onNext("jeden");
            log.info("emitting dwa");
            emitter.onNext("dwa");

            emitter.onComplete();
            log.info("Create ends");
        });

        obs
                .subscribeOn(Schedulers.io())
                .subscribe(s -> log.info("Received : {}", s));

        obs
                .subscribe(s -> log.info("Received : {}", s));
        TimeUnit.MILLISECONDS.sleep(3_000);

    }

    @Test
    public void shouldCreateObservable() throws Exception {
        Observable<String> example = Observable.create(emitter -> {
            try {
                String result = somethingThatThrowsException();
                emitter.onNext(result);
            } catch (Exception e) {
                log.error(e.toString());
                emitter.onError(e);
            }
            emitter.onComplete();
        });

        example.test();
    }

    @Test
    public void delayTest() throws Exception {
        Observable
                .just("Lorem", "ipsum", "dolor", "sit", "amet",
                        "consectetur", "adipiscing", "elit")
                .delay(10, SECONDS)
                .subscribe(System.out::println);

        SECONDS.sleep(15);

    }

    String somethingThatThrowsException() throws Exception {
        TimeUnit.MILLISECONDS.sleep(1_000);
        Double c = Math.random() * 10;
        log.info(c.toString());
        if (c > 1) throw new Exception("gt 9");
        return c.toString();
    }
}
