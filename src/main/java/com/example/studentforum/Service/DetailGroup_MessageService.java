package com.example.studentforum.Service;

import com.example.studentforum.Repository.DetailGroup_MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailGroup_MessageService {
    @Autowired
    private DetailGroup_MessageRepository detailGroup_MessageRepository;
}
