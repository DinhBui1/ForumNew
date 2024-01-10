package com.example.studentforum.Service;

import com.example.studentforum.Repository.Content_GroupMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Content_GroupMessageService {
    @Autowired
    private Content_GroupMessageRepository content_groupMessageRepository;
}
