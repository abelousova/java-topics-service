package ru.test.abelousova.topics.model.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class FileUtils {
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static List<String> getSubdirs(File directory) {
        String[] directories = directory.list((current, name) -> new File(current, name).isDirectory());

        return Arrays.asList(directories);
    }

    public static List<Date> convertDirsToDates(List<String> dirs, DateFormat dateFormat) {
        List<Date> dates = new ArrayList<>();
        for (String dir : dirs) {
            try {
                dates.add(dateFormat.parse(dir));
            } catch(ParseException e) {
                log.warn("Illegal time format, dir: " + dir);
            }
        }

        return dates;
    }
}
