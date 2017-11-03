package com.jsieczak.demo.rxjava.utils;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class FlightDataReader {
    private final static String ae4FilePath = "/home/jakub/workspace/rxJavaDemo/src/test/java/com/jsieczak/demo/rxjava/flightData/a4e.csv";
    private final static String f14FilePath = "/home/jakub/workspace/rxJavaDemo/src/test/java/com/jsieczak/demo/rxjava/flightData/f14a.csv";

    public static Flowable<String> getA4eData() {
        return loadFile(ae4FilePath);
    }

    public static Flowable<String> getF14Data() {
        return loadFile(f14FilePath);
    }

    private static Flowable<String> loadFile(final String fileName) {
        return Flowable.create(emitter -> {
            try (final BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
                br
                        .lines()
                        .map(flightDataRow -> {
                            if (!emitter.isCancelled()) {
                                try {
                                    final String processedFlightData = computeItHeavy(flightDataRow);
                                    emitter.onNext(processedFlightData);
                                } catch (InterruptedException exception) {
                                    emitter.onError(exception);
                                }
                            }
                            return flightDataRow;
                        }).toArray();
                emitter.onComplete();
            } catch (IOException exception) {
                emitter.onError(exception);
            }
        }, BackpressureStrategy.ERROR)  ;
    }

    private static String computeItHeavy(final String dataToProcess) throws InterruptedException {
        TimeUnit.SECONDS.sleep(2L);
        return dataToProcess;
    }
}
