package ru.test.abelousova.topics.model.impl;

import ru.test.abelousova.topics.model.api.Topic;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TopicImpl implements Topic {
    private File history;
    private Date lastTimestamp;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-kk-mm-ss");

    public TopicImpl(String name, File base) {
        history = new File(base, name + File.separator + "history");
        if (!history.isDirectory()) {
            throw new IllegalStateException("No 'history' dir in topic: " + name);
        }

        List<String> subdirs = FileUtils.getSubdirs(history);
        List<Date> timestamps = FileUtils.convertDirsToDates(subdirs, dateFormat);
        if (timestamps.isEmpty()) {
            throw new IllegalStateException("Empty history");
        }

        lastTimestamp = Collections.max(timestamps);
    }

    public String getLastTimestamp() {
        return dateFormat.format(lastTimestamp);
    }

    public TopicRecordImpl getLastRecord() {
        return new TopicRecordImpl(getLastTimestamp(), history);
    }
}
