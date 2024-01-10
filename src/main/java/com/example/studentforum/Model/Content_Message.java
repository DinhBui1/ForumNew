package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Content_message")
public class Content_Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Contentid")
    private int contentid;


    @Column(name = "Content")
    private String content;

    @Column(name = "Createday")
    private LocalDateTime createday;

    @Column(name = "Updateday")
    private LocalDateTime updateday;

    @ManyToOne
    @JoinColumn(name = "message_content", referencedColumnName = "messageid")
    private Message message_content;

    @ManyToOne
    @JoinColumn(name = "user_content", referencedColumnName = "userid")
    private User user_content;
}
