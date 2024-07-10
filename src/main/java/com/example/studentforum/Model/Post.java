package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Postid")
    private int postid;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false, referencedColumnName = "userid")
    private User user_post;

    @Column(name = "Title", columnDefinition = "LONGTEXT")
    private String title;
    @Column(name = "Content", columnDefinition = "LONGTEXT")
    private String content;
    @Column(name = "Createday")
    private LocalDateTime createday;
    @Column(name = "Updateday")
    private LocalDateTime updateday;
    @Column(name = "Totalread")
    private int totalread;
    @Column(name = "Image", columnDefinition = "LONGTEXT")
    private String image;
    @Column(name = "Requiredreputation")
    private int requiredreputation;
    @Column(name = "Ishide")
    private int ishide;

    @ManyToOne
    @JoinColumn(name = "groupid", referencedColumnName = "groupid")
    private Group group_post;

    @OneToMany(mappedBy = "post_comment", cascade = CascadeType.ALL)
    private List<Comment> listComment;

    @OneToMany(mappedBy = "post_bookmark", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bookmark> listBookmark;

    @OneToMany(mappedBy = "post_postlike", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post_Like> listPostlike;

    @OneToMany(mappedBy = "post_posttopic", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post_Topic> listPosttopic;

    @OneToMany(mappedBy = "post_report", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Report> report;

    @OneToMany(mappedBy = "post_view", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ViewPost> listViewPost;

    @Column(name = "Warning")
    private String warning;

    @Column(name = "Warningword")
    private String warningword;

    public Post(int postid, User user_post, String content, LocalDateTime createday, int ishide) {
        this.postid = postid;
        this.user_post = user_post;
        this.content = content;

        this.createday = createday;
        this.ishide = ishide;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalread() {
        return totalread;
    }

    public int getRequiredreputation() {
        return requiredreputation;
    }

    public void setRequiredreputation(int requiredreputation) {
        this.requiredreputation = requiredreputation;
    }

    public void setTotalread(int totalread) {
        this.totalread = totalread;
    }

    public int getIshide() {
        return ishide;
    }

    public void setIshide(int ishide) {
        this.ishide = ishide;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Post() {
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public User getUser_post() {
        return user_post;
    }

    public void setUser_post(User user_post) {
        this.user_post = user_post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public List<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(List<Comment> listComment) {
        this.listComment = listComment;
    }

    public List<Bookmark> getListBookmark() {
        return listBookmark;
    }

    public void setListBookmark(List<Bookmark> listBookmark) {
        this.listBookmark = listBookmark;
    }

    public List<Post_Like> getListPostlike() {
        return listPostlike;
    }

    public void setListPostlike(List<Post_Like> listPostlike) {
        this.listPostlike = listPostlike;
    }
}
