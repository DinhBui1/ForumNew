package com.example.studentforum.Control;

import com.example.studentforum.DTO.DetailMessageDTO;
import com.example.studentforum.Model.DetailMessage;
import com.example.studentforum.Model.Message;
import com.example.studentforum.Service.MessageService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @MutationMapping
    public Message create_message(@Argument String userid1, @Argument String userid2) {
        return messageService.createMessage(userid1, userid2);
    }

    @SubscriptionMapping
    public Publisher<List<DetailMessageDTO>> sub_detail_message_by_userid(@Argument String userid) {
        return messageService.getMessagebyUserid(userid);
    }
}
