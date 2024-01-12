package com.example.studentforum.Control;

import com.example.studentforum.Service.ContentGroupMessage_IconService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentGroupMessage_IconController {
    @Autowired
    private ContentGroupMessage_IconService contentGroupMessage_iconService;

    @MutationMapping
    public String create_content_groupmessage_icon(@Argument int iconid, @Argument int contentgroupmessageid) throws AuthenticationException {
        return contentGroupMessage_iconService.createContentGroupMessage_Icon(iconid, contentgroupmessageid);
    }
}
