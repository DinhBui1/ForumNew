package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@Table(name = "GroupF")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Groupid")
    private int groupid;

    @Column(name = "Groupname")
    private String groupname;

    @Column(name = "Image")
    private String image;

    @Column(name = "Createday")
    private LocalDateTime createday;

    @Column(name = "Reputation")
    private int reputation;

    @ManyToOne
    @JoinColumn(name = "admin", nullable = false, referencedColumnName = "userid")
    private User user_group;

    @Column(name = "Description")
    private String description;

    @OneToMany(mappedBy = "group_usergroup", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User_Group> listGroup;

    @OneToMany(mappedBy = "group_post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> listPost;

    @Column(name = "Chanel")
    private Integer chanel;
}
