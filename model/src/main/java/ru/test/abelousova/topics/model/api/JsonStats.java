package ru.test.abelousova.topics.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

public class JsonStats {
    @JsonProperty
    private BigInteger sum;

    @JsonProperty
    private long max;

    @JsonProperty
    private long min;

    @JsonProperty
    private double average;

    public JsonStats(BigInteger sum, long max, long min, double average) {
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.average = average;
    }

    public BigInteger getSum() {
        return sum;
    }

    public long getMax() {
        return max;
    }

    public long getMin() {
        return min;
    }

    public double getAverage() {
        return average;
    }
}
