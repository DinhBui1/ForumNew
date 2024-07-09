package com.example.studentforum.Service;

import com.example.studentforum.Model.Topic;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.TopicRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;

    public String createTopic(String topicname, String image, String color) {
        Topic t = new Topic();
        t.setCreateday(LocalDateTime.now());
        t.setIsdelete(0);
        t.setIshide(0);
        t.setTopicname(topicname);
        t.setImage(image);
        t.setColor(color);
        topicRepository.save(t);
        return "Create Topic Success";
    }

    public List<Topic> getallTopic() {
        return topicRepository.findAll();
    }

    public String deleteTopic(int topicid) {
        Topic p = topicRepository.getTopicById(topicid);
        topicRepository.delete(p);
        return "Delete Topic Success";
    }

    public List<Topic> getTopicByName(String name) {
        return topicRepository.getTopicByName(name);
    }

    public Topic updateTopic(Topic topic) {
        Topic t = topicRepository.getTopicById(topic.getTopicid());
        t.setColor(topic.getColor());
        return t;
    }
}
