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
    public String addContentMessage(@Argument String content, @Argument String messageid, @Argument String userid) {
        return contentMessageService.addContentMessage(content, messageid, userid);
    }

    @MutationMapping
    public String deleteContentMessage(@Argument String contentid, @Argument String userid) {
        return contentMessageService.deleteContentMessage(contentid, userid);
    }

    @MutationMapping
    public String updateContentMessage(@Argument String contentid, @Argument String content, @Argument String userid) {
        return contentMessageService.updateContentMessage(contentid, content, userid);
    }

    @QueryMapping
    public List<Content_Message> getContentMessageByMessageid(@Argument String messageid) {
        return contentMessageService.getContentMessageByMessageid(messageid);
    }

}
