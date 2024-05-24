package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "content_messager_icon")
public class ContentMessager_Icon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_groupmessage_iconid")
    private int content_messager_iconid;

    @ManyToOne
    @JoinColumn(name = "content_iconcontentgroupmessage")
    private Content_Message content_iconcontentmessage;

    @ManyToOne
    @JoinColumn(name = "icoicon_contentmessagenid")
    private Icon icon_contentmessage;

    @ManyToOne
    @JoinColumn(name = "user_iconcontentmessage")
    private User user_iconcontentmessage;

    @Column(name = "createday")
    private LocalDateTime createday;
}
