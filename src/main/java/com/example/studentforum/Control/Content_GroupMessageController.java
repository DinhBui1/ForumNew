package com.example.studentforum.Control;

import com.example.studentforum.Model.Content_GroupMessage;
import com.example.studentforum.Service.Content_GroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Content_GroupMessageController {
    @Autowired
    private Content_GroupMessageService content_groupMessageService;

    @MutationMapping
    public String create_content_groupmessage(@Argument String content, @Argument int groupmessageId, @Argument String userId, @Argument int messageresponseid) {
        return content_groupMessageService.createContent_GroupMessage(content, groupmessageId, userId, messageresponseid);
    }

    @MutationMapping
    public String update_content_groupmessage(@Argument int contentId, @Argument String content) {
        return content_groupMessageService.updateContent_GroupMessage(contentId, content);
    }

    @MutationMapping
    public String delete_content_groupmessage(@Argument int contentId) {
        return content_groupMessageService.deleteContent_GroupMessage(contentId);
    }

    @QueryMapping
    public List<Content_GroupMessage> get_content_groupmessage_by_groupmessageid(@Argument int groupmessageId) {
        return content_groupMessageService.getContent_GroupMessagebyGroupmessageidandUserid(groupmessageId);
    }

}
