package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Content_GroupMessage")
public class Content_GroupMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Content_groupmessageid")
    private int content_groupmessageid;

    @Column(name = "Content")
    private String content;

    @Column(name = "Createday")
    private LocalDateTime createday;

    @Column(name = "Updateday")
    private LocalDateTime updateday;

    @ManyToOne
    @JoinColumn(name = "detailgroupmessage_content", referencedColumnName = "detailgroup_messageid")
    private DetailGroup_Message detailgroupmessage_content;

    @ManyToOne
    @JoinColumn(name = "user_contentgroup", referencedColumnName = "userid")
    private User user_contentgroup;
}
