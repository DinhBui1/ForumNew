package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "content_groupmessage_icon")
public class ContentGroupMessage_Icon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_groupmessage_iconid")
    private int content_groupmessage_iconid;

    @ManyToOne
    @JoinColumn(name = "content_iconcontentgroupmessage")
    private Content_GroupMessage content_iconcontentgroupmessage;

    @ManyToOne
    @JoinColumn(name = "icon_iconcontentgroupmessage")
    private Icon icon_iconcontentgroupmessage;

    @ManyToOne
    @JoinColumn(name = "user_iconcontentgroupmessage")
    private User user_iconcontentgroupmessage;

    @Column(name = "createday")
    private LocalDateTime createday;
}
