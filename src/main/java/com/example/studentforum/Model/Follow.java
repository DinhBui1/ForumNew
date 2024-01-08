package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "Follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Followid")
    private int followid;

    @ManyToOne
    @JoinColumn(name = "user_follow",referencedColumnName = "userid")
    private User user_follow;

    @ManyToOne
    @JoinColumn(name = "user_follower",referencedColumnName = "userid")
    private User user_follower;

    @Column(name = "Createday")
    private LocalDateTime createday;

    public int getFollowid() {
        return followid;
    }

    public void setFollowid(int followid) {
        this.followid = followid;
    }

    public User getUser_follow() {
        return user_follow;
    }

    public void setUser_follow(User user_follow) {
        this.user_follow = user_follow;
    }

    public User getUser_follower() {
        return user_follower;
    }

    public void setUser_follower(User user_follower) {
        this.user_follower = user_follower;
    }

    public LocalDateTime getCreateday() {
        return createday;
    }

    public void setCreateday(LocalDateTime createday) {
        this.createday = createday;
    }
}
