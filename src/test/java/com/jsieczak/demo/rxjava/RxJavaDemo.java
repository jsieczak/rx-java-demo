package com.jsieczak.demo.rxjava;

import com.jsieczak.demo.rxjava.utils.FlightDataReader;
import com.jsieczak.demo.rxjava.utils.MeasurePoint;
import com.jsieczak.demo.rxjava.utils.MeasurementPointMapper;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.concurrent.TimeUnit.SECONDS;


public class RxJavaDemo {

    private static final Logger log = LoggerFactory.getLogger(RxJavaDemo.class);

    private Predicate<? super BigDecimal> isGreaterThan(final long number) {
        return bigDecimal -> bigDecimal.compareTo(new BigDecimal(number)) == 1;
    }


    @Test
    public void create() throws Exception {

        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("jeden");
            emitter.onNext("dwa");
            emitter.onComplete();
        });
    }

    @Test
    public void createAndSubscribeTo() throws Exception {
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("jeden");
            emitter.onNext("dwa");
            emitter.onComplete();
        });

        Disposable d = observable.subscribe(
                s -> log.info("onNext " + s),
                error -> {
                    log.info("onError");
                    error.printStackTrace();
                },
                () -> log.info("onComplete"),
                disposable -> log.info("onSubscribe")
        );

    }

    @Test
    public void subscribeOnTest() throws Exception {

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

        TimeUnit.MILLISECONDS.sleep(4_000);

    }

    @Test
    public void asynchronousObservableNewThread() throws Exception {
        Observable<String> obs = Observable.create(emitter -> {
            log.info("Create begin");
            new Thread(() -> {
                log.info("emitting jeden");
                emitter.onNext("jeden");
                log.info("emitting dwa");
                emitter.onNext("dwa");
                emitter.onComplete();
                log.info("Create ends");
            }).start();
        });


        obs
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(s -> log.info("Received : {}", s));
        obs
                .subscribe(s -> log.info("Received : {}", s));

        TimeUnit.MILLISECONDS.sleep(4_000);
    }

    @Test
    public void delayTest() throws Exception {
        Observable
                .just("Lorem", "ipsum", "dolor", "sit", "amet",
                        "consectetur", "adipiscing", "elit")
                .delay(5, SECONDS)
                .subscribe(System.out::println);

        SECONDS.sleep(8);

    }

    @Test
    public void delayEachItemTest() throws Exception {
        Observable.zip(Observable.just("Lorem", "ipsum", "dolor", "sit", "amet",
                "consectetur", "adipiscing", "elit"),
                Observable.interval(2L, SECONDS),
                (text, time) -> text)
                .subscribe(System.out::println);

        SECONDS.sleep(19);

    }

    @Test
    public void flightDataZipWith() throws Exception {
        Flowable<String> ae4Data = FlightDataReader
                .getA4eData()
                .skip(1L)
                .take(5L);

        Flowable<String> f14Data = FlightDataReader
                .getF14Data()
                .skip(1L)
                .take(5L);

        f14Data
                .zipWith(ae4Data, Pair::of)
                .subscribe((s) -> {
                    log.info("left: " + s.getLeft());
                    log.info("right: " + s.getRight());
                });

    }

    @Test
    public void flightData() throws Exception {
//tu skonczylem
        final MeasurementPointMapper mapper = dataRow -> {
            List<BigDecimal> m = Arrays
                    .stream(dataRow.split(","))
                    .map(BigDecimal::new)
                    .collect(Collectors.toList());

            return new MeasurePoint(m.get(0), m.get(1), m.get(2),
                    m.get(3), m.get(4), m.get(5), m.get(6));
        };

        FlightDataReader
                .getF14Data()
                .skip(1L)
                .map(mapper::parseToMeasurePoint)
                .map(MeasurePoint::getTime)
                .takeUntil(isGreaterThan(90L))
                .subscribe(time -> log.info(time.toString()));
    }

    private String somethingThatThrowsException() throws Exception {
        TimeUnit.MILLISECONDS.sleep(1_000);
        Double c = Math.random() * 10;
        log.info(c.toString());
        if (c > 1) throw new Exception("gt 9");
        return c.toString();
    }

    @Test
    public void certyfikatDemo() throws Exception {

        int[]a1=new int[]{1,2,3};
        int[]b1=new int[]{1,2,3};


        assert(Arrays.equals(a1,b1));
    }
}
