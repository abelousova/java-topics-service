package ru.test.abelousova.topics.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.test.abelousova.topics.model.api.Stats;
import ru.test.abelousova.topics.model.api.Topic;
import ru.test.abelousova.topics.model.api.TopicsService;
import ru.test.abelousova.topics.model.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/topics")
public class TopicsRestController {
    private static final Logger log = LoggerFactory.getLogger(TopicsRestController.class);

    @Autowired
    private TopicsService topicsService;

    @RequestMapping(value = "/", method = GET)
    List<String> listTopics() {
        return topicsService.getTopicsList();
    }

    @RequestMapping(value = "/{name}", method = GET)
    String getLastTimestamp(@PathVariable String name) {
        if (!topicsService.topicExists(name)) {
            throw new NotFoundException("Topic not found");
        }

        Topic topic = topicsService.getTopic(name);
        return topic.getLastTimestamp();
    }

    @RequestMapping(value = "/{name}/stats", method = GET)
    Stats getStats(@PathVariable String name) {
        if (!topicsService.topicExists(name)) {
            throw new NotFoundException("Topic not found");
        }

        Topic topic = topicsService.getTopic(name);
        return topic.getLastRecord().getStats();

    }

    @RequestMapping(value = "/{name}/partitions", method = GET)
    Map<Integer, Long> getPartitions(@PathVariable String name) {
        if (!topicsService.topicExists(name)) {
            throw new NotFoundException("Topic not found");
        }

        Topic topic = topicsService.getTopic(name);
        return topic.getLastRecord().getPartitions();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody String handleNotFound(HttpServletRequest req, NotFoundException ex) {
        return "Not found";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody String handleException(HttpServletRequest req, Exception ex) {
        log.error("Exception caught", ex);
        return "Exception " + ex;
    }
}
