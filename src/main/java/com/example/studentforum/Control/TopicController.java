package com.example.studentforum.Control;

import com.example.studentforum.Model.Topic;
import com.example.studentforum.Service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TopicController {
    @Autowired
    private TopicService topicService;

    @MutationMapping
    public String create_topic(@Argument String topicname) {
        return topicService.createTopic(topicname);
    }

    @MutationMapping
    public String delete_topic(@Argument int topicid) {
        return topicService.deleteTopic(topicid);
    }

    @QueryMapping
    public List<Topic> topic() {
        return topicService.getallTopic();
    }

    @QueryMapping
    public List<Topic> find_topic_by_topicname(@Argument String topicname) {
        return topicService.getTopicByName(topicname);
    }
}
