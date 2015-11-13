package ru.test.abelousova.topics.model.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.test.abelousova.topics.model.api.TopicsService;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Service
public class DirectoryBasedTopicsService implements TopicsService {
    private File directory;
    private List<String> topics;

    @Autowired
    public DirectoryBasedTopicsService(@Value("${ru.test.abelousova.topics.base_dir:../base_dir}") String directory) {
        this.directory = new File(directory);
        if (!this.directory.isDirectory()) {
            throw new IllegalArgumentException("Illegal base directory");
        }
        topics = FileUtils.getSubdirs(this.directory);
    }

    @Override
    public List<String> getTopicsList() {
        return Collections.unmodifiableList(topics);
    }

    @Override
    public TopicImpl getTopic(String name) {
        if (!topics.contains(name)) {
            throw new IllegalArgumentException("Topic not found");
        }
        return new TopicImpl(name, directory);
    }

    @Override
    public boolean topicExists(String name) {
        return topics.contains(name);
    }
}