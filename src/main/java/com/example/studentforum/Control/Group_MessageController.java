package com.example.studentforum.Control;

import com.example.studentforum.Service.Group_MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Group_MessageController {
    @Autowired
    private Group_MessageService group_messageService;
}
