package com.example.studentforum.Service;

import com.example.studentforum.Model.Content_Message;
import com.example.studentforum.Model.Message;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.Content_MessageRepositpry;
import com.example.studentforum.Repository.MessageRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String addContentMessage(String content, String messageid, String userid) {
        User u = userRepository.getUserById(userid);
        if (u == null) return "User not found";
        Message message = messageRepository.getMessagesByMessageid(messageid, userid);
        if (message == null) return "Message not found";
        Content_Message contentMessage = new Content_Message();
        contentMessage.setContent(content);
        contentMessage.setCreateday(LocalDateTime.now());
        contentMessage.setMessage_content(message);
        contentMessage.setUser_content(u);
        contentMessageRepositpry.save(contentMessage);
        return "Add content message success";
    }

    public String deleteContentMessage(String contentid, String userid) {
        User u = userRepository.getUserById(userid);
        if (u == null) return "User not found";
        Content_Message contentMessage = contentMessageRepositpry.getContent_MessageByContentid(contentid);
        if (contentMessage == null) return "Content message not found";
        if (contentMessage.getUser_content().getUserid().equals(userid)) {
            contentMessageRepositpry.delete(contentMessage);
            return "Delete content message success";
        }
        return "Delete content message fail";
    }

    public String updateContentMessage(String contentid, String content, String userid) {
        User u = userRepository.getUserById(userid);
        if (u == null) return "User not found";
        Content_Message contentMessage = contentMessageRepositpry.getContent_MessageByContentid(contentid);
        if (contentMessage == null) return "Content message not found";
        if (contentMessage.getUser_content().getUserid().equals(userid)) {
            contentMessage.setContent(content);
            contentMessage.setUpdateday(LocalDateTime.now());
            contentMessageRepositpry.save(contentMessage);
            return "Update content message success";
        }
        return "Update content message fail";
    }

    public List<Content_Message> getContentMessageByMessageid(String messageid) {
        return contentMessageRepositpry.getContent_MessageByMessageid(messageid);
    }
}
