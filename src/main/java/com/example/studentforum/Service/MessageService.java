package com.example.studentforum.Service;

import com.example.studentforum.Model.Message;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.MessageRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public String createMessage(String userid1, String userid2) {
        User u1 = userRepository.getUserById(userid1);
        User u2 = userRepository.getUserById(userid2);
        if (u1 == null || u2 == null) {
            return "User isn't exit";
        }
        Message m1 = new Message();
        m1.setMessageid(u1.getMssv() + "_" + u2.getMssv());
        m1.setUser_message(u1);
        m1.setIsblock(0);
        m1.setCreateday(LocalDateTime.now());
        m1.setMessagename(u1.getFullname());
        messageRepository.save(m1);

        Message m2 = new Message();
        m2.setMessageid(u1.getMssv() + "_" + u2.getMssv());
        m2.setUser_message(u2);
        m2.setIsblock(0);
        m2.setCreateday(LocalDateTime.now());
        m2.setMessagename(u2.getFullname());
        messageRepository.save(m2);

        return "Create message success";
    }

    public String blockMessage(String messageid, String userid, int isblock) {
        Message m = messageRepository.getMessagesByMessageid(messageid, userid);
        if (m == null) {
            return "Message isn't exit";
        }

        m.setIsblock(isblock);
        return "Block message success";
    }

}
