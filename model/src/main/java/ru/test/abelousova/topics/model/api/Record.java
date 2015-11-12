package ru.test.abelousova.topics.model.api;

import java.util.Map;

public interface Record {
    public Map<Integer, Long> getPartitions();
    public JsonStats getStats();
}
