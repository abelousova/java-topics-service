package ru.test.abelousova.topics.model.impl.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.test.abelousova.topics.model.impl.TopicImpl;

import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TopicImplTest.ContextConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class TopicImplTest {
    private static final File TEST_DIR = new File("./src/test/resourses/test_dir");

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public TopicImpl topic() {
            return new TopicImpl("topic", TEST_DIR);
        }
    }

    @Resource(name = "topic")
    private TopicImpl topic;

    @Test
    public void testGetLastTimestamp() {
        Assert.assertEquals("2015-11-09-15-56-26", topic.getLastTimestamp());
    }

    @Test
    public void testGetLastRecord() {
        Map<Integer, Long> partitions = new HashMap<>();
        partitions.put(15651, 5879465L);
        partitions.put(15612, 4521212L);
        partitions.put(64651, 54631132L);
        Assert.assertEquals(partitions, topic.getLastRecord().getPartitions());
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateTopicWithoutHistory() {
        new TopicImpl("topic3", TEST_DIR);
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateTopicWithEmptyHistory() {
        new TopicImpl("topic5", TEST_DIR);
    }
}
