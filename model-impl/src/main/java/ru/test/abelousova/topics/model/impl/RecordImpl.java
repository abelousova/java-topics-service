package ru.test.abelousova.topics.model.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.test.abelousova.topics.model.api.Record;
import ru.test.abelousova.topics.model.api.JsonStats;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RecordImpl implements Record {
    private Map<Integer, Long> partitions = new HashMap<>();

    public RecordImpl(String timestamp, File path) {
        File offsets = new File(path, timestamp + File.separator + "offsets.csv");
        if (!offsets.isFile()) {
            throw new IllegalStateException("Can't get file offsets.csv in path " + path);
        }
        try {
            CSVParser csvParser = CSVParser.parse(offsets, Charset.defaultCharset(), CSVFormat.DEFAULT);
            for (CSVRecord record : csvParser) {
                int key = Integer.parseInt(record.get(0));
                long value = Long.parseLong(record.get(1));
                partitions.put(key, value);
            }
        } catch (IOException | NumberFormatException e) {
            throw new IllegalStateException(e);
        }

    }

    public Map<Integer, Long> getPartitions() {
        return Collections.unmodifiableMap(partitions);
    }

    public JsonStats getStats() {
        if (partitions.isEmpty()) {
            throw new IllegalStateException("Empty partitions, can't get stats");
        }
        BigInteger sum = getSum();
        return new JsonStats(sum, getMax(), getMin(), getAverage(sum));
    }

    private BigInteger getSum() {
        BigInteger sum = BigInteger.ZERO;
        for (Long messageCount : partitions.values()) {
            sum = sum.add(BigInteger.valueOf(messageCount));
        }
        return sum;
    }

    private long getMax() {
        return Collections.max(partitions.values());
    }

    private long getMin() {
        return Collections.min(partitions.values());
    }

    private double getAverage(BigInteger sum) {
        double decimalSum = sum.doubleValue();
        return decimalSum / partitions.size();
    }
}
