package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "Comment_Like")
public class Comment_Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Commentlikeid")
    private int commentlikeid;

    @ManyToOne
    @JoinColumn(name = "commentid",nullable = false,referencedColumnName = "commentid")
    private Comment comment_commentlike;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false,referencedColumnName = "userid")
    private User user_commentlike;

    @ManyToOne
    @JoinColumn(name = "iconid",nullable = false,referencedColumnName = "iconid")
    private Icon icon_commentlike;

    @Column(name = "Createday")
    private LocalDateTime createday;
    public Comment_Like(int commentlikeid, Comment comment_commentlike, User user_commentlike, Icon icon_commentlike) {
        this.commentlikeid = commentlikeid;
        this.comment_commentlike = comment_commentlike;
        this.user_commentlike = user_commentlike;
        this.icon_commentlike = icon_commentlike;
    }

    public Comment_Like() {
    }

    public LocalDateTime getCreateday() {
        return createday;
    }

    public void setCreateday(LocalDateTime createday) {
        this.createday = createday;
    }

    public int getCommentlikeid() {
        return commentlikeid;
    }

    public void setCommentlikeid(int commentlikeid) {
        this.commentlikeid = commentlikeid;
    }

    public Comment getComment_commentlike() {
        return comment_commentlike;
    }

    public void setComment_commentlike(Comment comment_commentlike) {
        this.comment_commentlike = comment_commentlike;
    }

    public User getUser_commentlike() {
        return user_commentlike;
    }

    public void setUser_commentlike(User user_commentlike) {
        this.user_commentlike = user_commentlike;
    }

    public Icon getIcon_commentlike() {
        return icon_commentlike;
    }

    public void setIcon_commentlike(Icon icon_commentlike) {
        this.icon_commentlike = icon_commentlike;
    }
}
