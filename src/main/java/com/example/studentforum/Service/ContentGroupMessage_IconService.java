package com.example.studentforum.Service;

import com.example.studentforum.Authetication.JwtAuthenticationToken;
import com.example.studentforum.Model.ContentGroupMessage_Icon;
import com.example.studentforum.Model.Content_GroupMessage;
import com.example.studentforum.Model.Icon;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.ContentGroupMessage_IconRepository;
import com.example.studentforum.Repository.Content_GroupMessageRepository;
import com.example.studentforum.Repository.IconRepository;
import com.example.studentforum.Repository.UserRepository;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ContentGroupMessage_IconService {
    @Autowired
    private ContentGroupMessage_IconRepository contentGroupMessage_iconRepository;
    @Autowired
    private Content_GroupMessageRepository content_groupMessageRepository;
    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private UserRepository userRepository;

    public String createContentGroupMessage_Icon(int iconid, int contentgroupmessageid) throws AuthenticationException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
            User user = userRepository.getUserById(useridtoken);
            if (user == null) {
                return "User not found";
            }
            Icon icon = iconRepository.getIconById(iconid);
            if (icon == null) {
                return "Icon not found";
            }
            Content_GroupMessage content_groupMessage = content_groupMessageRepository.findByContentId(contentgroupmessageid);
            if (content_groupMessage == null) {
                return "Content not found";
            }
            ContentGroupMessage_Icon cgmi = contentGroupMessage_iconRepository.getContentGroupMessage_IconByUseridandMessageid(useridtoken, contentgroupmessageid);
            if (cgmi == null) {
                ContentGroupMessage_Icon contentGroupMessage_icon = new ContentGroupMessage_Icon();
                contentGroupMessage_icon.setContent_iconcontentgroupmessage(content_groupMessage);
                contentGroupMessage_icon.setIcon_iconcontentgroupmessage(icon);
                contentGroupMessage_icon.setUser_iconcontentgroupmessage(user);
                contentGroupMessage_icon.setCreateday(LocalDateTime.now());
                contentGroupMessage_iconRepository.save(contentGroupMessage_icon);
                return "Create ContentGroupMessage_Icon success";
            } else {
                if (cgmi.getIcon_iconcontentgroupmessage().getIconid() == iconid) {
                    contentGroupMessage_iconRepository.delete(cgmi);
                    return "Delete ContentGroupMessage_Icon success";
                } else {
                    Icon icon1 = iconRepository.getIconById(iconid);
                    cgmi.setIcon_iconcontentgroupmessage(icon1);
                    cgmi.setCreateday(LocalDateTime.now());
                    contentGroupMessage_iconRepository.save(cgmi);
                    return "Update ContentGroupMessage_Icon success";
                }
            }
        } catch (Exception e) {
            throw new AuthenticationException("Create ContentGroupMessage_Icon fail");
        }
    }
}
