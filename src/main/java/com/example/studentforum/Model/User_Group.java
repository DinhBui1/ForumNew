package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Getter
@Setter
@Table(name = "User_Group")
public class User_Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_groupid")
    private int user_groupid;

    @Column(name = "Createday")
    private LocalDateTime createday;


    @ManyToOne
    @JoinColumn(name = "userid",referencedColumnName = "userid")
    private User user_usergroup;

    @ManyToOne
    @JoinColumn(name = "groupid",nullable = false,referencedColumnName = "groupid")
    private Group group_usergroup;
    @Column(name = "Checked")
    private int checked;

}
