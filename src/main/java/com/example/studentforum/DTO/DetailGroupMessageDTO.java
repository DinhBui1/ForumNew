package com.example.studentforum.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DetailGroupMessageDTO {

    private int group_messageid;

    private String group_messagename;

    private String group_messageimage;

    private String group_messagedescription;

    private LocalDateTime createdate;

}
