package com.example.studentforum.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupDTO {

    private int groupid;

    private String groupname;

    private String image;

    private LocalDateTime createday;

    private int reputation;

    private String admin;

    private String description;

    private Integer chanel;

    private int totaluser;
}
