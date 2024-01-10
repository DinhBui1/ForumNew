package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Group_message")
public class Group_Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Group_messageid")
    private int group_messageid;

    @Column(name = "Group_messagename")
    private String group_messagename;

    @OneToMany(mappedBy = "detailgroupmessage_groupmessage", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetailGroup_Message> detailgroupmessage_groupmessage;
}
