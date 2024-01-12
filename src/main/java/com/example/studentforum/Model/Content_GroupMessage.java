package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "groupmessage_content", referencedColumnName = "group_messageid")
    private Group_Message groupmessage_content;

    @ManyToOne
    @JoinColumn(name = "user_contentgroup", referencedColumnName = "userid")
    private User user_contentgroup;

    @OneToMany(mappedBy = "contentGroupMessageResponse", cascade = CascadeType.ALL)
    private List<Content_GroupMessage> contentGroupMessages;

    @ManyToOne
    @JoinColumn(name = "contentGroupMessageResponse")
    @JsonIgnore
    private Content_GroupMessage contentGroupMessageResponse;

    @OneToMany(mappedBy = "content_iconcontentgroupmessage", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ContentGroupMessage_Icon> content_iconcontentgroupmessage;
}
