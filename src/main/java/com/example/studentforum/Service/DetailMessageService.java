package com.example.studentforum.Service;

import com.example.studentforum.Model.DetailMessage;
import com.example.studentforum.Model.Message;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.DetailMessageRepository;
import com.example.studentforum.Repository.MessageRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DetailMessageService {
    @Autowired
    private DetailMessageRepository detailMessageRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public String createDetailMessage(int messageid, String userid) {
        User u = userRepository.getUserById(userid);
        if (u == null) {
            return "User isn't exit";
        }
        Message m = messageRepository.getMessagesByMessageid(messageid);
        if (m == null) {
            return "Message isn't exit";
        }
        DetailMessage detailMessage = new DetailMessage();
        detailMessage.setDetailmessage_message(m);
        detailMessage.setUser_detailmessage(u);
        detailMessage.setIsblock(0);
        detailMessageRepository.save(detailMessage);
        return "Create detail message success";
    }

    public String blockDetailMessage(int messageid, String userid, int isblock) {
        DetailMessage detailMessage = detailMessageRepository.getDetailMessageByMessageidandUserid(messageid, userid);
        if (detailMessage == null) {
            return "Detail message isn't exit";
        }
        detailMessage.setIsblock(isblock);
        detailMessageRepository.save(detailMessage);
        return "Block detail message success";
    }

    public List<DetailMessage> getDetailMessageByUserid(String userid) {
        User u = userRepository.getUserById(userid);
        if (u == null) {
            return null;
        }
        List<DetailMessage> messages = detailMessageRepository.getDetailMessageByUserid(userid);
        if (messages.isEmpty()) {
            return null;
        }
        List<DetailMessage> detailMessages = new ArrayList<>();
        for (DetailMessage detailMessage : messages) {
            DetailMessage detailMessage1 = detailMessageRepository.getDetailMessageByMessageid(detailMessage.getDetailmessage_message().getMessageid(), userid);
            detailMessages.add(detailMessage1);
        }
        return detailMessages;
    }
}
