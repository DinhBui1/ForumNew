package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "DetailGroup_Message")
public class DetailGroup_Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Detailgroup_messageid")
    private int detailgroup_messageid;

    @ManyToOne
    @JoinColumn(name = "detailgroupmessage_groupmessage", referencedColumnName = "group_messageid")
    private Group_Message detailgroupmessage_groupmessage;

    @ManyToOne
    @JoinColumn(name = "user_detailgroupmessage", referencedColumnName = "userid")
    private User user_detailgroupmessage;

    @Column(name = "Level")
    private int level;

    @Column(name = "Createday")
    private LocalDateTime createday;

    @Column(name = "Lastseen")
    private LocalDateTime lastseen;

    @Column(name = "Ishide")
    private Integer ishide;
}
