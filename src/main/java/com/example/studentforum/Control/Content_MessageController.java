package com.example.studentforum.Control;

import com.example.studentforum.Model.Content_Message;
import com.example.studentforum.Service.Content_MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Content_MessageController {
    @Autowired
    private Content_MessageService contentMessageService;

    @MutationMapping
    public String create_content_message(@Argument String content, @Argument int messageid, @Argument String userid, @Argument int messageresponseid) {
        return contentMessageService.addContentMessage(content, messageid, userid, messageresponseid);
    }

    @MutationMapping
    public String delete_content_message(@Argument int contentid) {
        return contentMessageService.deleteContentMessage(contentid);
    }

    @MutationMapping
    public String update_content_message(@Argument int contentid, @Argument String content) {
        return contentMessageService.updateContentMessage(contentid, content);
    }

    @QueryMapping
    public List<Content_Message> get_content_message_by_messageid(@Argument int messageid) {
        return contentMessageService.getContentMessageByMessageid(messageid);
    }
}
