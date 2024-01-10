package com.example.studentforum.Service;

import com.example.studentforum.Repository.Group_MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Group_MessageService {
    @Autowired
    private Group_MessageRepository group_messageRepository;
    
}
