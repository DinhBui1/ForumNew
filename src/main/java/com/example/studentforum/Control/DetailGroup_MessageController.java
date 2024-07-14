package com.example.studentforum.Control;

import com.example.studentforum.Model.DetailGroup_Message;
import com.example.studentforum.Service.DetailGroup_MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DetailGroup_MessageController {
    @Autowired
    private DetailGroup_MessageService detailGroup_messageService;

    @MutationMapping
    public DetailGroup_Message join_group_message(@Argument int groupmessageid, @Argument String userid, @Argument int level) {
        return detailGroup_messageService.createDetailGroup_Message(groupmessageid, userid, level);
    }

    @MutationMapping
    public String update_level_detailgroup_message(@Argument int groupmessageid, @Argument String userid, @Argument int level) {
        return detailGroup_messageService.updateLevelDetailGroup_Message(groupmessageid, userid, level);
    }

    @MutationMapping
    public String leave_group_message(@Argument int groupmessageid, @Argument String userid) {
        return detailGroup_messageService.deleteDetailGroup_Message(groupmessageid, userid);
    }

    @QueryMapping
    public List<DetailGroup_Message> get_detailgroup_message_by_userid(@Argument String userid) {
        return detailGroup_messageService.getDetailGroup_MessageByUserid(userid);
    }

    @MutationMapping
    public String update_lastseen_group(@Argument int groupmessageid) {
        return detailGroup_messageService.updateLastseenGroup(groupmessageid);
    }
}
