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
import java.util.List;

@Service
public class DetailGroup_MessageService {
    @Autowired
    private DetailGroup_MessageRepository detailGroup_MessageRepository;
    @Autowired
    private Group_MessageRepository group_messageRepository;
    @Autowired
    private UserRepository userRepository;

    public DetailGroup_Message createDetailGroup_Message(int group_messageid, String userid, int level) {
        try {
            Group_Message gm = group_messageRepository.getGroup_MessageById(group_messageid);
            if (gm == null) {
                throw new RuntimeException("Group_Message Not Found");
            }
            User u = userRepository.getUserById(userid);
            if (u == null) {
                throw new RuntimeException("User Not Found");
            }
            DetailGroup_Message dgm1 = detailGroup_MessageRepository.getDetailGroup_MessageByMessageidandUserid(group_messageid, userid);
            if (dgm1 != null) {
                throw new RuntimeException("DetailGroup_Message Already Exists");
            }
            DetailGroup_Message dgm = new DetailGroup_Message();
            dgm.setDetailgroupmessage_groupmessage(gm);
            dgm.setUser_detailgroupmessage(u);
            dgm.setLevel(level);
            dgm.setLastseen(LocalDateTime.now());
            dgm.setCreateday(LocalDateTime.now());
            detailGroup_MessageRepository.save(dgm);
            return dgm;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String updateLevelDetailGroup_Message(int groupmessageid, String userid, int level) {
        try {
            DetailGroup_Message dgm = detailGroup_MessageRepository.getDetailGroup_MessageByMessageidandUserid(groupmessageid, userid);
            if (dgm == null) {
                return "DetailGroup_Message Not Found";
            }
            dgm.setLevel(level);
            detailGroup_MessageRepository.save(dgm);
            return "Update Level DetailGroup_Message Success";
        } catch (Exception e) {
            return "Update Level DetailGroup_Message Fail";
        }
    }

    public String deleteDetailGroup_Message(int groupmessageid, String userid) {
        try {
            DetailGroup_Message dgm = detailGroup_MessageRepository.getDetailGroup_MessageByMessageidandUserid(groupmessageid, userid);
            if (dgm == null) {
                return "DetailGroup_Message Not Found";
            }
            detailGroup_MessageRepository.delete(dgm);
            return "Delete DetailGroup_Message Success";
        } catch (Exception e) {
            return "Delete DetailGroup_Message Fail";
        }
    }

    public List<DetailGroup_Message> getDetailGroup_MessageByUserid(String userid) {
        try {
            User u = userRepository.getUserById(userid);
            if (u == null) {
                return null;
            }
            List<DetailGroup_Message> detailGroup_messages = detailGroup_MessageRepository.getDetailGroup_MessageByUserid(userid);
            if (detailGroup_messages.isEmpty()) {
                return null;
            }
            return detailGroup_messages;
        } catch (Exception e) {
            return null;
        }
    }

    public String updateLastseenGroup(int dtgroupmessageid) {
        try {
            DetailGroup_Message dgm = detailGroup_MessageRepository.getDetailGroup_MessageByid(dtgroupmessageid);
            if (dgm == null) {
                return "DetailGroup_Message Not Found";
            }
            dgm.setLastseen(LocalDateTime.now());
            detailGroup_MessageRepository.save(dgm);
            return "Update Lastseen DetailGroup_Message Success";
        } catch (Exception e) {
            return "Update Lastseen DetailGroup_Message Fail";
        }
    }
}
