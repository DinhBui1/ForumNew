package com.example.studentforum.Control;

import com.example.studentforum.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @MutationMapping
    public String create_message(@Argument String userid1, @Argument String userid2) {
        return messageService.createMessage(userid1, userid2);
    }
    
}
