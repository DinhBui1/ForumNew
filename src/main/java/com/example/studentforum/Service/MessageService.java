package com.example.studentforum.Service;

import com.example.studentforum.Model.DetailMessage;
import com.example.studentforum.Model.Message;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.MessageRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DetailMessageService detailMessageService;

    public String createMessage(String userid1, String userid2) {
        User u1 = userRepository.getUserById(userid1);
        User u2 = userRepository.getUserById(userid2);
        Message messages = messageRepository.getMessagesByMessagename(u1.getMssv() + "_" + u2.getMssv());
        if (messages != null) {
            return "Message is exit";
        }
        if (u1 == null || u2 == null) {
            return "User isn't exit";
        }
        Message m = new Message();
        m.setMessagename(u1.getMssv() + "_" + u2.getMssv());
        m.setCreateday(LocalDateTime.now());
        messageRepository.save(m);
        detailMessageService.createDetailMessage(m.getMessageid(), u1.getUserid());
        detailMessageService.createDetailMessage(m.getMessageid(), u2.getUserid());
        return "Create message success";
    }

}
