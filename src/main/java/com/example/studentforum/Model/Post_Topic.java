package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Post_Topic")
public class Post_Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Posttopicid")
    private int posttopicid;

    @ManyToOne
    @JoinColumn(name = "postid", nullable = false, referencedColumnName = "postid")
    @JsonIgnore
    private Post post_posttopic;

    @ManyToOne
    @JoinColumn(name = "topicid", nullable = false, referencedColumnName = "topicid")
    @JsonIgnore
    private Topic topic_posttopic;

    @Column(name = "Createday")
    private LocalDateTime createday;
}
