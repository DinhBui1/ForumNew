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
    @Column(name = "Messageid")
    private String messageid;

    @Column(name = "Messagename")
    private String messagename;

    @Column(name = "Createday")
    private LocalDateTime createday;
    
    @ManyToOne
    @JoinColumn(name = "user_message", referencedColumnName = "userid")
    private User user_message;

    @Column(name = "Isblock")
    private int isblock;

    @OneToMany(mappedBy = "message_content", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Content_Message> content_messages;


}
