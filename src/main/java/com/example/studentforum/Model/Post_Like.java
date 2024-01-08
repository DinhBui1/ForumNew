package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "Post_Like")
public class Post_Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Postlikeid")
    private int postlikeid;

    @ManyToOne
    @JoinColumn(name = "postid",nullable = false,referencedColumnName = "postid")
    @JsonIgnore
    private Post post_postlike;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false,referencedColumnName = "userid")
    private User user_postlike;

    @ManyToOne
    @JoinColumn(name = "iconid",nullable = false,referencedColumnName = "iconid")
    private Icon icon_postlike;

    @Column(name = "Createday")
    private LocalDateTime createday;
    public Post_Like(int postlikeid, Post post_postlike, User user_postlike, Icon icon_postlike) {
        this.postlikeid = postlikeid;
        this.post_postlike = post_postlike;
        this.user_postlike = user_postlike;
        this.icon_postlike = icon_postlike;
    }

    public Post_Like() {
    }

    public LocalDateTime getCreateday() {
        return createday;
    }

    public void setCreateday(LocalDateTime createday) {
        this.createday = createday;
    }

    public int getPostlikeid() {
        return postlikeid;
    }

    public void setPostlikeid(int postlikeid) {
        this.postlikeid = postlikeid;
    }

    public Post getPost_postlike() {
        return post_postlike;
    }

    public void setPost_postlike(Post post_postlike) {
        this.post_postlike = post_postlike;
    }

    public User getUser_postlike() {
        return user_postlike;
    }

    public void setUser_postlike(User user_postlike) {
        this.user_postlike = user_postlike;
    }

    public Icon getIcon_postlike() {
        return icon_postlike;
    }

    public void setIcon_postlike(Icon icon_postlike) {
        this.icon_postlike = icon_postlike;
    }
}
