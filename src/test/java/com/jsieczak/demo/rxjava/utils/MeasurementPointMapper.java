package com.jsieczak.demo.rxjava.utils;

@FunctionalInterface
public interface MeasurementPointMapper {
    MeasurePoint parseToMeasurePoint(String dataRow);
}
