package com.example.studentforum.DTO;

import com.example.studentforum.Model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PostDTO {

    private int postid;

    private User user_post;

    private String title;

    private String content;

    private LocalDateTime createday;

    private Date updateday;

    private int totalread;

    private String image;

    private int requiredreputation;

    private int ishide;

    private Group group_post;

    private int totallike;

    private int totaldislike;

    private int totalcomment;

    private List<Post_Topic> listtopic;
}
