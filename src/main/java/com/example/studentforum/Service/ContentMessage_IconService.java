package com.example.studentforum.Service;

import com.example.studentforum.Authetication.JwtAuthenticationToken;
import com.example.studentforum.Model.ContentMessager_Icon;
import com.example.studentforum.Repository.ContentMessage_IconRepository;
import com.example.studentforum.Repository.Content_MessageRepositpry;
import com.example.studentforum.Repository.IconRepository;
import com.example.studentforum.Repository.UserRepository;
import kotlin.jvm.internal.SerializedIr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContentMessage_IconService {

    @Autowired
    private ContentMessage_IconRepository contentMessageIconRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private Content_MessageRepositpry contentMessageRepositpry;

    public String createIconContentMessage(int contentmessageid, int iconid) {
        ContentMessager_Icon contentMessager_icon = new ContentMessager_Icon();
        String userid = ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getUserid();
        ContentMessager_Icon check = contentMessageIconRepository.getContentMessage_IconByContentMessageidandIconidandUserid(contentmessageid, userid);
        if (check != null) {
            if (check.getIcon_contentmessage().getIconid() == iconid) {
                contentMessageIconRepository.delete(check);
                return "Delete icon content message success";
            } else {
                check.setIcon_contentmessage(iconRepository.getIconById(iconid));
                check.setCreateday(LocalDateTime.now());
                contentMessageIconRepository.save(check);
                return "Update icon content message success";
            }
        } else {
            contentMessager_icon.setContent_iconcontentmessage(contentMessageRepositpry.getContent_MessageByContentid(contentmessageid));
            contentMessager_icon.setIcon_contentmessage(iconRepository.getIconById(iconid));
            contentMessager_icon.setCreateday(LocalDateTime.now());
            contentMessager_icon.setUser_iconcontentmessage(userRepository.getUserById(userid));
            contentMessageIconRepository.save(contentMessager_icon);
        }

        return "Create icon content message success";
    }
}
