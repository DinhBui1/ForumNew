package com.example.studentforum.Control;

import com.example.studentforum.Service.Content_GroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Content_GroupMessageController {
    @Autowired
    private Content_GroupMessageService content_groupMessageService;
}
