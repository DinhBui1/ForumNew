package com.example.studentforum.Service;

import com.example.studentforum.Authetication.JwtAuthenticationToken;
import com.example.studentforum.Model.DetailMessage;
import com.example.studentforum.Model.Group_Message;
import com.example.studentforum.Model.Message;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.DetailMessageRepository;
import com.example.studentforum.Repository.MessageRepository;
import com.example.studentforum.Repository.UserRepository;
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
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DetailMessageService detailMessageService;
    @Autowired
    private DetailMessageRepository detailMessageRepository;

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

    public Publisher<List<DetailMessage>> getMessagebyUserid() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String useridtoken = ((JwtAuthenticationToken) authentication).getUserid();
            return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
                List<DetailMessage> messages = detailMessageRepository.findDetailMessagesByUserIdOrderByLatestMessage(useridtoken);
                List<DetailMessage> detailMessages = new ArrayList<>();
                for (DetailMessage detailMessage : messages) {
                    DetailMessage detailMessage1 = detailMessageRepository.getDetailMessageByMessageid(detailMessage.getDetailmessage_message().getMessageid(), useridtoken);
                    detailMessages.add(detailMessage1);
                }
                subscriber.onNext(detailMessages);
            }, 0, 2, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
