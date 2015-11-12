package ru.test.abelousova.topics.model.impl.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.test.abelousova.topics.model.api.JsonStats;
import ru.test.abelousova.topics.model.impl.RecordImpl;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RecordImplTest.ContextConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class RecordImplTest {
    private static final File TEST_DIR = new File("./src/test/resourses/test_dir");

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public RecordImpl record() {
            return new RecordImpl("2015-11-12-12-35-26",
                    new File(TEST_DIR, "topic6" + File.separator + "history"));
        }

        @Bean
        public RecordImpl emptyRecord() {
            return new RecordImpl("2015-11-11-17-15-20",
                    new File(TEST_DIR, "topic1" + File.separator + "history"));
        }
    }

    @Resource(name = "record")
    private RecordImpl record;

    @Resource(name = "emptyRecord")
    private RecordImpl emptyRecord;

    @Test
    public void testGetPartitions() {
        Map<Integer, Long> partitions = new HashMap<>();
        partitions.put(1, 48745L);
        partitions.put(2, 7986453L);
        partitions.put(3, 2045L);
        Assert.assertEquals(partitions, record.getPartitions());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testPartitionsUnmodifiable() {
        record.getPartitions().put(212, 5461L);
    }

    @Test
    public void testGetStats() {
        JsonStats stats = record.getStats();
        Assert.assertEquals(BigInteger.valueOf(8037243L), stats.getSum());
        Assert.assertEquals(7986453L, stats.getMax());
        Assert.assertEquals(2045L, stats.getMin());
        Assert.assertEquals(2679081.0, stats.getAverage(), 0.01);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetStatsFromEmptyRecord() {
        emptyRecord.getStats();
    }

    @Test
    public void testGetPartitionsFromEmptyRecord() {
        Assert.assertEquals(new HashMap<>(), emptyRecord.getPartitions());
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateRecordWithoutOffsets() {
        new RecordImpl("2015-11-11-17-18-52",
                new File(TEST_DIR, "topic2" + File.separator + "history"));
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateRecordWithIllegalOffsets() {
        new RecordImpl("2015-11-11-17-19-52",
                new File(TEST_DIR, "topic4" + File.separator + "history"));
    }
}
