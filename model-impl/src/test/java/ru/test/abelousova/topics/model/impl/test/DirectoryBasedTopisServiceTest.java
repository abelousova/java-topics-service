package ru.test.abelousova.topics.model.impl.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.test.abelousova.topics.model.impl.DirectoryBasedTopicsService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DirectoryBasedTopisServiceTest.ContextConfiguration.class,
                      loader=AnnotationConfigContextLoader.class)
public class DirectoryBasedTopisServiceTest {
    private static final String TEST_DIR = "./src/test/resourses/test_dir";

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public DirectoryBasedTopicsService topicsService() {
            return new DirectoryBasedTopicsService(TEST_DIR);
        }
    }

    @Autowired
    private DirectoryBasedTopicsService topicsService;

    @Test
    public void testGetTopicsList() {
        List<String> topicsListExpected = new ArrayList<>();
        topicsListExpected.add("topic");
        topicsListExpected.add("topic1");
        topicsListExpected.add("topic2");
        topicsListExpected.add("topic3");
        topicsListExpected.add("topic4");
        topicsListExpected.add("topic5");
        topicsListExpected.add("topic6");

        List<String> topicsListActual = topicsService.getTopicsList();

        // Compare arrays regardless order
        Assert.assertTrue(topicsListActual.containsAll(topicsListExpected) &&
                topicsListExpected.containsAll(topicsListActual));
    }

    @Test
    public void testTopicExists() {
        Assert.assertTrue(topicsService.topicExists("topic"));
    }

    @Test
    public void testTopicNotExists() {
        Assert.assertFalse(topicsService.topicExists("uefsadj"));
    }

    @Test
    public void testGetTopic() {
        Map<Integer, Long> partitions = new HashMap<>();
        partitions.put(15651, 5879465L);
        partitions.put(15612, 4521212L);
        partitions.put(64651, 54631132L);
        Assert.assertEquals(partitions, topicsService.getTopic("topic").getLastRecord().getPartitions());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTopicNotExists() {
        topicsService.getTopic("uesgf");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalBaseDirectory() {
        new DirectoryBasedTopicsService(TEST_DIR + File.separator + "hjewavhzn");
    }
}
