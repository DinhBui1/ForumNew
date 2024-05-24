package com.example.studentforum.Model;

import lombok.Data;

@Data
public class UserActivity {

    private String userid;
    private Integer postid;
    private String title;
    private String content;
    private String topicname;
}
