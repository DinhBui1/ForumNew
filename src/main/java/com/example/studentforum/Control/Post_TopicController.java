package com.example.studentforum.Control;

import com.example.studentforum.Service.Post_TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Post_TopicController {

    @Autowired
    private Post_TopicService postTopicService;
}
