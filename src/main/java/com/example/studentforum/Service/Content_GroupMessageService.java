package com.example.studentforum.Service;

import com.example.studentforum.Authetication.JwtAuthenticationToken;
import com.example.studentforum.Model.ContentGroupMessage_Icon;
import com.example.studentforum.Model.Content_GroupMessage;
import com.example.studentforum.Model.Group_Message;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.ContentGroupMessage_IconRepository;
import com.example.studentforum.Repository.Content_GroupMessageRepository;
import com.example.studentforum.Repository.Group_MessageRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Content_GroupMessageService {
    @Autowired
    private Content_GroupMessageRepository content_groupMessageRepository;
    @Autowired
    private Group_MessageRepository group_messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContentGroupMessage_IconRepository contentGroupMessage_iconRepository;

    public String createContent_GroupMessage(String content, int groupmessageId, String userId, int messageresponseid) {
        Group_Message group_message = group_messageRepository.getGroup_MessageById(groupmessageId);
        if (group_message == null) {
            return "Group message not found";
        }
        User user = userRepository.getUserById(userId);
        if (user == null) {
            return "User not found";
        }
        Content_GroupMessage content_groupMessage = new Content_GroupMessage();
        content_groupMessage.setContent(content);
        content_groupMessage.setGroupmessage_content(group_message);
        content_groupMessage.setUser_contentgroup(user);
        content_groupMessage.setCreateday(LocalDateTime.now());
        if (messageresponseid != 0) {
            Content_GroupMessage dm = content_groupMessageRepository.findByContentId(messageresponseid);
            content_groupMessage.setContentGroupMessageResponse(dm);
        }
        content_groupMessageRepository.save(content_groupMessage);
        return "Create content group message successfully";
    }

    public String updateContent_GroupMessage(int contentId, String content) {
        Content_GroupMessage content_groupMessage = content_groupMessageRepository.findByContentId(contentId);
        if (content_groupMessage == null) {
            return "Content group message not found";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
        if (!useridtoken.equals(content_groupMessage.getUser_contentgroup().getUserid())) {
            return "You are not owner of this content group message";
        }

        content_groupMessage.setContent(content);
        content_groupMessage.setUpdateday(LocalDateTime.now());
        content_groupMessageRepository.save(content_groupMessage);
        return "Update content group message successfully";
    }

    public String deleteContent_GroupMessage(int contentId) {
        Content_GroupMessage content_groupMessage = content_groupMessageRepository.findByContentId(contentId);
        if (content_groupMessage == null) {
            return "Content group message not found";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
        if (!useridtoken.equals(content_groupMessage.getUser_contentgroup().getUserid())) {
            return "You are not owner of this content group message";
        }
        List<Content_GroupMessage> content_groupMessages = content_groupMessageRepository.findByContentResponseId(contentId);
        if (content_groupMessages != null) {
            for (Content_GroupMessage c : content_groupMessages) {
                c.setContentGroupMessageResponse(null);
                content_groupMessageRepository.save(c);
            }
        }
        List<ContentGroupMessage_Icon> cgmi = contentGroupMessage_iconRepository.getContentGroupMessage_IconByMessageid(contentId);
        if (cgmi != null) {
            for (ContentGroupMessage_Icon c : cgmi) {
                contentGroupMessage_iconRepository.delete(c);
            }
        }
        content_groupMessageRepository.delete(content_groupMessage);
        return "Delete content group message successfully";
    }

    public List<Content_GroupMessage> getContent_GroupMessagebyGroupmessageidandUserid(int groupmessageId) {
        return content_groupMessageRepository.getContent_GroupMessagebyGroupmessageidandUserid(groupmessageId);
    }


}
