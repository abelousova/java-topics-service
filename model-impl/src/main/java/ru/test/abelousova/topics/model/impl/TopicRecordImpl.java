package ru.test.abelousova.topics.model.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.test.abelousova.topics.model.api.TopicRecord;
import ru.test.abelousova.topics.model.api.JsonStats;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TopicRecordImpl implements TopicRecord {
    private Map<Integer, Long> partitions = new HashMap<>();

    public TopicRecordImpl(String timestamp, File path) {
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
        long max = Collections.max(partitions.values());
        long min = Collections.min(partitions.values());
        double average = sum.doubleValue() / partitions.size();

        return new JsonStats(sum, max, min, average);
    }

    private BigInteger getSum() {
        BigInteger sum = BigInteger.ZERO;
        for (Long messageCount : partitions.values()) {
            sum = sum.add(BigInteger.valueOf(messageCount));
        }
        return sum;
    }
}
