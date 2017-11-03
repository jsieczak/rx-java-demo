package com.jsieczak.demo.rxjava.utils;

import java.math.BigDecimal;

public class MeasurePoint {
    private final BigDecimal time;
    private final BigDecimal longitude;
    private final BigDecimal latitude;
    private final BigDecimal altitude;
    private final BigDecimal roll;
    private final BigDecimal pitch;
    private final BigDecimal yaw;

    public MeasurePoint(final BigDecimal time,
                        final BigDecimal longitude,
                        final BigDecimal latitude,
                        final BigDecimal altitude,
                        final BigDecimal roll,
                        final BigDecimal pitch,
                        final BigDecimal yaw) {
        this.time = time;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.roll = roll;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public BigDecimal getTime() {
        return time;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getAltitude() {
        return altitude;
    }

    public BigDecimal getRoll() {
        return roll;
    }

    public BigDecimal getPitch() {
        return pitch;
    }

    public BigDecimal getYaw() {
        return yaw;
    }
}
