package ru.test.abelousova.topics.model.api;

import java.util.List;

public interface TopicsService {
    List<String> getTopicsList();

    Topic getTopic(String name);

    boolean topicExists(String name);
}
