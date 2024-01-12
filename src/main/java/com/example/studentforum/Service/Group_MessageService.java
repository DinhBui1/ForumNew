package com.example.studentforum.Service;

import com.example.studentforum.Authetication.JwtAuthenticationToken;
import com.example.studentforum.Model.DetailGroup_Message;
import com.example.studentforum.Model.Group_Message;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.DetailGroup_MessageRepository;
import com.example.studentforum.Repository.Group_MessageRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class Group_MessageService {
    @Autowired
    private Group_MessageRepository group_messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DetailGroup_MessageRepository detailGroup_messageRepository;
    @Autowired
    private DetailGroup_MessageService detailGroup_messageService;
    @Autowired
    private Content_GroupMessageService content_groupMessageService;

    public String createGroup_Message(Group_Message group_message, String userid) {
        try {
            User u = userRepository.getUserById(userid);
            if (u == null) {
                return "User Not Found";
            }
            Group_Message gm = new Group_Message();
            gm.setGroup_messagename(group_message.getGroup_messagename());
            gm.setGroup_messageimage(group_message.getGroup_messageimage());
            gm.setGroup_messagedescription(group_message.getGroup_messagedescription());
            gm.setCreatedate(LocalDateTime.now());
            group_messageRepository.save(gm);

            detailGroup_messageService.createDetailGroup_Message(gm.getGroup_messageid(), userid, 1);
            return "Create Group_Message Success";
        } catch (Exception e) {
            return "Create Group_Message Fail";
        }
    }

    public String updateGroup_Message(Group_Message group_message) {
        try {
            Group_Message gm = group_messageRepository.getGroup_MessageById(group_message.getGroup_messageid());
            if (gm == null) {
                return "Group_Message Not Found";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
            DetailGroup_Message dgm = detailGroup_messageRepository.getDetailGroup_MessageByMessageidandUserid(group_message.getGroup_messageid(), useridtoken);
            if (dgm.getLevel() != 1) {
                return "You are not owner of this Group_Message";
            }

            gm.setGroup_messagename(group_message.getGroup_messagename());
            gm.setGroup_messageimage(group_message.getGroup_messageimage());
            gm.setGroup_messagedescription(group_message.getGroup_messagedescription());
            group_messageRepository.updateGroup_Message(gm);
            return "Update Group_Message Success";
        } catch (Exception e) {
            return "Update Group_Message Fail";
        }
    }

    public String deleteGroup_Message(int groupmessageid) {
        try {
            Group_Message gm = group_messageRepository.getGroup_MessageById(groupmessageid);
            if (gm == null) {
                return "Group_Message Not Found";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
            DetailGroup_Message dgm = detailGroup_messageRepository.getDetailGroup_MessageByMessageidandUserid(groupmessageid, useridtoken);
            if (dgm.getLevel() != 1) {
                return "You are not owner of this Group_Message";
            }
            group_messageRepository.delete(gm);
            return "Delete Group_Message Success";
        } catch (Exception e) {
            return "Delete Group_Message Fail";
        }
    }
}
