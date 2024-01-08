package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "Viewpost")
public class ViewPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Viewid")
    private int viewid;

    @Column(name = "Createday")
    private LocalDateTime createday;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false,referencedColumnName = "userid")
    private User user_view;

    @ManyToOne
    @JoinColumn(name = "postid",referencedColumnName = "postid")
    private Post post_view;

    public int getViewid() {
        return viewid;
    }

    public void setViewid(int viewid) {
        this.viewid = viewid;
    }

    public LocalDateTime getCreateday() {
        return createday;
    }

    public void setCreateday(LocalDateTime createday) {
        this.createday = createday;
    }

    public User getUser_view() {
        return user_view;
    }

    public void setUser_view(User user_view) {
        this.user_view = user_view;
    }

    public Post getPost_view() {
        return post_view;
    }

    public void setPost_view(Post post_view) {
        this.post_view = post_view;
    }
}
