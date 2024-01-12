package com.example.studentforum.Control;

import com.example.studentforum.Model.Group_Message;
import com.example.studentforum.Service.Group_MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Group_MessageController {
    @Autowired
    private Group_MessageService group_messageService;

    @MutationMapping
    public String create_group_message(@Argument Group_Message group_message, @Argument String userid) {
        return group_messageService.createGroup_Message(group_message, userid);
    }

    @MutationMapping
    public String update_group_message(@Argument Group_Message group_message) {
        return group_messageService.updateGroup_Message(group_message);
    }
}
