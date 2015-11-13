package ru.test.abelousova.topics.model.api;

import java.util.Map;

public interface TopicRecord {
    Map<Integer, Long> getPartitions();
    JsonStats getStats();
}
