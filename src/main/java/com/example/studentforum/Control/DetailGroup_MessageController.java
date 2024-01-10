package com.example.studentforum.Control;

import com.example.studentforum.Service.DetailGroup_MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetailGroup_MessageController {
    @Autowired
    private DetailGroup_MessageService detailGroup_messageService;
}
