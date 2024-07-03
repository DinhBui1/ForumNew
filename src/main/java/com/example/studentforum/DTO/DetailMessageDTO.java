package com.example.studentforum.DTO;

import com.example.studentforum.Model.Message;
import com.example.studentforum.Model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailMessageDTO {

    private int detailmessageid;

    private Integer messageid;

    private User userid;

    private int isblock;

    private LocalDateTime lastseen;

    private LocalDateTime lastsend;
}
