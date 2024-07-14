package com.example.studentforum.Control;

import com.example.studentforum.DTO.DetailGroupMessageDTO;
import com.example.studentforum.Model.DetailGroup_Message;
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
    public DetailGroup_Message create_group_message(@Argument Group_Message group_message, @Argument String userid) {
        try {
            return group_messageService.createGroup_Message(group_message, userid);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
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
    public Publisher<List<DetailGroupMessageDTO>> sub_group_message_by_userid(@Argument String userid) {
        return group_messageService.getGroup_MessagebyUserid(userid);
    }
}
