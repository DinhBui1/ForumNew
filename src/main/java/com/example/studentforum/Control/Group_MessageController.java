package com.example.studentforum.Control;

import com.example.studentforum.Model.Group_Message;
import com.example.studentforum.Service.Group_MessageService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @MutationMapping
    public String delete_group_message(@Argument int group_messageid) {
        return group_messageService.deleteGroup_Message(group_messageid);
    }

    @QueryMapping
    public List<Group_Message> get_group_message_by_keyword(@Argument String keyword) {
        return group_messageService.getGroup_MessagebyKeyword(keyword);
    }

    @SubscriptionMapping
    public Publisher<List<Group_Message>> sub_group_message_by_userid() {
        return group_messageService.getGroup_MessagebyUserid();
    }
}
