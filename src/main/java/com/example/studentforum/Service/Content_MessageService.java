package com.example.studentforum.Service;

import com.example.studentforum.Authetication.JwtAuthenticationToken;
import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.Content_MessageRepositpry;
import com.example.studentforum.Repository.DetailMessageRepository;
import com.example.studentforum.Repository.MessageRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Content_MessageService {
    @Autowired
    private Content_MessageRepositpry contentMessageRepositpry;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private DetailMessageRepository detailMessageRepository;

    public String addContentMessage(String content, int messageid, String userid, int messageresponseid) {
        User u = userRepository.getUserById(userid);
        if (u == null) return "User not found";
        Message m = messageRepository.getMessagesByMessageid(messageid);
        Content_Message contentMessage = new Content_Message();
        contentMessage.setContent(content);
        contentMessage.setMessage_content(m);
        contentMessage.setCreateday(LocalDateTime.now());
        contentMessage.setUser_content(u);
        if (messageresponseid != 0) {
            Content_Message dm = contentMessageRepositpry.getContent_MessageByContentid(messageresponseid);
            contentMessage.setContentMessageResponse(dm);
        }
        contentMessageRepositpry.save(contentMessage);
        return "Add content message success";
    }

    public String deleteContentMessage(int contentid) {
        Content_Message contentMessage = contentMessageRepositpry.getContent_MessageByContentid(contentid);
        if (contentMessage == null) return "Content message not found";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
        if (!useridtoken.equals(contentMessage.getUser_content().getUserid())) {
            return "You are not owner of this content message";
        }
        contentMessageRepositpry.delete(contentMessage);
        return "Delete content message success";
    }

    public String updateContentMessage(int contentid, String content) {
        Content_Message contentMessage = contentMessageRepositpry.getContent_MessageByContentid(contentid);
        if (contentMessage == null) return "Content message not found";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
        if (!useridtoken.equals(contentMessage.getUser_content().getUserid())) {
            return "You are not owner of this content message";
        }
        contentMessage.setContent(content);
        contentMessage.setUpdateday(LocalDateTime.now());
        contentMessageRepositpry.save(contentMessage);
        return "Update content message success";
    }

    public List<Content_Message> getContentMessageByMessageid(int messageid) {
        return contentMessageRepositpry.getContent_MessageByMessage_content(messageid);
    }

}
