package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "Bookmark")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Bookmarkid")
    private int bookmarkid;

    @ManyToOne
    @JoinColumn(name = "postid",nullable = false,referencedColumnName = "postid")
    private Post post_bookmark;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false,referencedColumnName = "userid")
    private User user_bookmark;

    @Column(name = "Createday")
    private LocalDateTime createday;

    public Bookmark(int bookmarkid, Post post_bookmark, User user_bookmark) {
        this.bookmarkid = bookmarkid;
        this.post_bookmark = post_bookmark;
        this.user_bookmark = user_bookmark;
    }

    public Bookmark() {
    }

    public int getBookmarkid() {
        return bookmarkid;
    }

    public void setBookmarkid(int bookmarkid) {
        this.bookmarkid = bookmarkid;
    }

    public Post getPost_bookmark() {
        return post_bookmark;
    }

    public void setPost_bookmark(Post post_bookmark) {
        this.post_bookmark = post_bookmark;
    }

    public User getUser_bookmark() {
        return user_bookmark;
    }

    public void setUser_bookmark(User user_bookmark) {
        this.user_bookmark = user_bookmark;
    }

    public LocalDateTime getCreateday() {
        return createday;
    }

    public void setCreateday(LocalDateTime createday) {
        this.createday = createday;
    }
}
