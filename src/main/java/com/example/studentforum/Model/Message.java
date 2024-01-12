package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "Message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Messageid")
    private int messageid;

    @Column(name = "Messagename")
    private String messagename;

    @Column(name = "Createday")
    private LocalDateTime createday;

    @OneToMany(mappedBy = "detailmessage_message", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetailMessage> detailmessage_message;

    @OneToMany(mappedBy = "message_content", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Content_Message> message_content;
}
