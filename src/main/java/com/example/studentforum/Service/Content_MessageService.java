package com.example.studentforum.Service;

import com.example.studentforum.Authetication.JwtAuthenticationToken;
import com.example.studentforum.DTO.ContentMessageDTO;
import com.example.studentforum.DTO.TotalIcon;
import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.*;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private ContentMessage_IconRepository contentMessageIconRepository;

    public String addContentMessage(String content, String image, int messageid, String userid, int messageresponseid) {
        User u = userRepository.getUserById(userid);
        if (u == null) return "User not found";
        Message m = messageRepository.getMessagesByMessageid(messageid);
        Content_Message contentMessage = new Content_Message();
        contentMessage.setContent(content);
        contentMessage.setImage(image);
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

    public Publisher<List<ContentMessageDTO>> getContent_MessagebyMessageidandUserid(int messageId, String userid) {
        try {
            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
            DetailMessage dm = detailMessageRepository.getDetailMessageByMessageid(messageId, userid);
            if (dm == null) throw new RuntimeException("You are not in this message");
            return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
                List<Content_Message> cgm = contentMessageRepositpry.findMessagesByUserIdOrderByLatestMessage(messageId);
                List<ContentMessageDTO> listcontents = new ArrayList<>();
                for (Content_Message cm : cgm) {
                    ContentMessageDTO c = this.convertToContentMessageDTO(cm);
                    listcontents.add(c);
                }
                subscriber.onNext(listcontents);
            }, 0, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

//    public String createIconContentMessage(int contentid, int icon) {
//        Content_Message contentMessage = contentMessageRepositpry.getContent_MessageByContentid(contentid);
//        if (contentMessage == null) return "Content message not found";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
//        if (!useridtoken.equals(contentMessage.getUser_content().getUserid())) {
//            return "You are not owner of this content message";
//        }
//        Content_Message cm = contentMessageRepositpry.getContent_MessageByContentidandIconid(contentid, icon);
//        if (cm != null) {
//            if (cm.getIcon_contentmessage().getIconid() == icon) {
//                cm.setIcon_contentmessage(null);
//                contentMessageRepositpry.save(cm);
//                return "Delete icon content message success";
//            } else {
//                Icon i = iconRepository.getIconById(icon);
//                contentMessage.setIcon_contentmessage(i);
//                contentMessageRepositpry.save(contentMessage);
//                return "Update icon content message success";
//            }
//        }
//        Icon i = iconRepository.getIconById(icon);
//        contentMessage.setIcon_contentmessage(i);
//        contentMessage.setUpdateday(LocalDateTime.now());
//        contentMessageRepositpry.save(contentMessage);
//        return "Update icon content message success";
//    }

    public ContentMessageDTO convertToContentMessageDTO(Content_Message contentMessage) {
        ContentMessageDTO contentMessageDTO = new ContentMessageDTO();
        contentMessageDTO.setContentid(contentMessage.getContentid());
        contentMessageDTO.setUserid(contentMessage.getUser_content().getUserid());
        contentMessageDTO.setMessageid(contentMessage.getMessage_content().getMessageid());
        if (contentMessage.getContentMessageResponse() != null) {
            contentMessageDTO.setParentid(contentMessage.getContentMessageResponse().getContentid());
        } else {
            contentMessageDTO.setParentid(null);
        }
        contentMessageDTO.setContent(contentMessage.getContent());
        contentMessageDTO.setCreateday(contentMessage.getCreateday());
        contentMessageDTO.setUpdateday(contentMessage.getUpdateday());
        contentMessageDTO.setImage(contentMessage.getImage());
        List<ContentMessager_Icon> contentMessagerIcons = contentMessageIconRepository.getContentMessage_IconByContentMessageid(contentMessage.getContentid());
        List<TotalIcon> totalIcons = new ArrayList<>();
        for (ContentMessager_Icon cmi : contentMessagerIcons) {
            TotalIcon ti = new TotalIcon();
            if (totalIcons.size() == 0) {
                ti.setIconid(cmi.getIcon_contentmessage().getIconid());
                ti.setTotal(1);
                totalIcons.add(ti);
            } else {
                for (TotalIcon t : totalIcons) {
                    if (t.getIconid() == cmi.getIcon_contentmessage().getIconid()) {
                        t.setTotal(t.getTotal() + 1);
                        break;
                    } else {
                        ti.setIconid(cmi.getIcon_contentmessage().getIconid());
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
