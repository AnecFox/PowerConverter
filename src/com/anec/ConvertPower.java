package com.anec;

public class ConvertPower {

    private final static double METRIC_HORSEPOWER = 1.35965;

    public static double kilowattsToMetricHorsepower(double value) {
        return value * METRIC_HORSEPOWER;
    }

    public static double metricHorsepowerToKilowatts(double value) {
        return value / METRIC_HORSEPOWER;
    }
}
