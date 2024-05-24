package com.example.studentforum.Service;

import com.example.studentforum.Authetication.JwtAuthenticationToken;
import com.example.studentforum.DTO.ContentMessageDTO;
import com.example.studentforum.DTO.TotalIcon;
import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.*;
import graphql.kickstart.execution.subscriptions.SubscriptionException;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private DetailGroup_MessageRepository detailGroup_messageRepository;

    public String createContent_GroupMessage(String content, String image, int groupmessageId, String userId, int messageresponseid) {
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
        content_groupMessage.setImage(image);
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

    public String deleteContent_GroupMessageNotoken(int contentId) {
        Content_GroupMessage content_groupMessage = content_groupMessageRepository.findByContentId(contentId);
        if (content_groupMessage == null) {
            return "Content group message not found";
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

    public Publisher<List<ContentMessageDTO>> getContent_GroupMessagebyGroupmessageidandUseridNotoken(int groupmessageId, String userId) throws SubscriptionException {
        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
            DetailGroup_Message detailGroup_message = detailGroup_messageRepository.getDetailGroup_MessageByMessageidandUserid(groupmessageId, userId);
            if (detailGroup_message == null) {
                throw new RuntimeException("You are not in this group message");
            }
            return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
                List<Content_GroupMessage> cgm = content_groupMessageRepository.findGroupMessagesByUserIdOrderByLatestMessage(groupmessageId);
                List<ContentMessageDTO> listcontents = new ArrayList<>();
                for (Content_GroupMessage cm : cgm) {
                    ContentMessageDTO c = this.convertToContentMessageDTO(cm);
                    listcontents.add(c);
                }
                subscriber.onNext(listcontents);
            }, 0, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new SubscriptionException(e.getMessage());
        }
    }

    public ContentMessageDTO convertToContentMessageDTO(Content_GroupMessage contentMessage) {
        ContentMessageDTO contentMessageDTO = new ContentMessageDTO();
        contentMessageDTO.setContentid(contentMessage.getContent_groupmessageid());
        contentMessageDTO.setUserid(contentMessage.getUser_contentgroup().getUserid());
        contentMessageDTO.setMessageid(contentMessage.getGroupmessage_content().getGroup_messageid());
        if (contentMessage.getContentGroupMessageResponse() != null) {
            contentMessageDTO.setParentid(contentMessage.getContentGroupMessageResponse().getContent_groupmessageid());
        } else {
            contentMessageDTO.setParentid(null);
        }
        contentMessageDTO.setImage(contentMessage.getImage());
        contentMessageDTO.setContent(contentMessage.getContent());
        contentMessageDTO.setCreateday(contentMessage.getCreateday());
        contentMessageDTO.setUpdateday(contentMessage.getUpdateday());
        List<ContentGroupMessage_Icon> contentMessagerIcons =
                contentGroupMessage_iconRepository.getContentGroupMessage_IconByMessageid(contentMessage.getContent_groupmessageid());
        List<TotalIcon> totalIcons = new ArrayList<>();
        for (ContentGroupMessage_Icon cmi : contentMessagerIcons) {
            TotalIcon ti = new TotalIcon();
            if (totalIcons.size() == 0) {
                ti.setIconid(cmi.getIcon_iconcontentgroupmessage().getIconid());
                ti.setTotal(1);
                totalIcons.add(ti);
            } else {
                for (TotalIcon t : totalIcons) {
                    if (t.getIconid() == cmi.getIcon_iconcontentgroupmessage().getIconid()) {
                        t.setTotal(t.getTotal() + 1);
                        break;
                    } else {
                        ti.setIconid(cmi.getIcon_iconcontentgroupmessage().getIconid());
                        ti.setTotal(1);
                        totalIcons.add(ti);
                        break;
                    }
                }
            }
        }
        contentMessageDTO.setTotalicon(totalIcons);
        return contentMessageDTO;
    }

}
