package com.example.studentforum.Control;

import com.example.studentforum.Model.DetailMessage;
import com.example.studentforum.Service.DetailMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DetailMessageController {
    @Autowired
    private DetailMessageService detailMessageService;

    @MutationMapping
    public String block_message(@Argument int messageid, @Argument String userid, @Argument int isblock) {
        return detailMessageService.blockDetailMessage(messageid, userid, isblock);
    }

    @QueryMapping
    public List<DetailMessage> get_detail_message_by_userid(@Argument String userid) {
        return detailMessageService.getDetailMessageByUserid(userid);
    }
}
