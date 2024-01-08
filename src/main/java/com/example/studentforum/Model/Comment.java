package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Commentid")
    private int commentid;

    @OneToMany(mappedBy = "comment_comment",cascade = CascadeType.ALL)
    private List<Comment> listComment;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false,referencedColumnName = "userid")
    private User user_comment;

    @ManyToOne
    @JoinColumn(name = "postid",referencedColumnName = "postid")
    @JsonIgnore
    private Post post_comment;

    @ManyToOne
    @JoinColumn(name = "comment_commentid")
    @JsonIgnore
    private Comment comment_comment;

    @Column(name = "Content",columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "Createday")
    private LocalDateTime createday;
    @Column(name = "Updateday")
    private LocalDateTime updateday;

    @OneToMany(mappedBy = "comment_commentlike",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment_Like> listCommentlike;

    @OneToMany(mappedBy = "comment_report",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Report> report;

    public Comment() {
    }

    public Comment(int commentid, User user_comment, Post post_comment, String content, LocalDateTime createday) {
        this.commentid = commentid;
        this.user_comment = user_comment;
        this.post_comment = post_comment;
        this.content = content;

        this.createday = createday;

    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public User getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(User user_comment) {
        this.user_comment = user_comment;
    }

    public Post getPost_comment() {
        return post_comment;
    }

    public void setPost_comment(Post post_comment) {
        this.post_comment = post_comment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public LocalDateTime getCreateday() {
        return createday;
    }

    public void setCreateday(LocalDateTime createday) {
        this.createday = createday;
    }

    public LocalDateTime getUpdateday() {
        return updateday;
    }

    public void setUpdateday(LocalDateTime updateday) {
        this.updateday = updateday;
    }

    public List<Comment_Like> getListCommentlike() {
        return listCommentlike;
    }

    public void setListCommentlike(List<Comment_Like> listCommentlike) {
        this.listCommentlike = listCommentlike;
    }
}
