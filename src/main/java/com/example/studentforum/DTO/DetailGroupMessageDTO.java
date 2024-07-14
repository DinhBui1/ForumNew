package com.example.studentforum.DTO;

import com.example.studentforum.Model.Group_Message;
import com.example.studentforum.Model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailGroupMessageDTO {

    private int detailgroupmessageid;

    private Group_Message groupmessage;

    private User userid;

    private Integer ishide;

    private LocalDateTime lastseen;

    private LocalDateTime lastsend;

}
