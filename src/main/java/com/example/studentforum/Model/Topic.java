package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Topicid")
    private int topicid;
    @Column(name = "Topicname")
    private String topicname;
    @Column(name = "Createday")
    private LocalDateTime createday;
    @Column(name = "Ishide")
    private int ishide;
    @Column(name = "Isdelete")
    private int isdelete;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false,referencedColumnName = "userid")
    private User user_topic;

    @OneToMany(mappedBy = "topic_post",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> listPost;

    public Topic(int topicid, String topicname, LocalDateTime createday, int ishide, int isdelete, User user_topic) {
        this.topicid = topicid;
        this.topicname = topicname;
        this.createday = createday;
        this.ishide = ishide;
        this.isdelete = isdelete;
        this.user_topic = user_topic;
    }

    public Topic() {
    }

    public int getIshide() {
        return ishide;
    }

    public void setIshide(int ishide) {
        this.ishide = ishide;
    }

    public int getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public LocalDateTime getCreateday() {
        return createday;
    }

    public void setCreateday(LocalDateTime createday) {
        this.createday = createday;
    }

    public User getUser_topic() {
        return user_topic;
    }

    public void setUser_topic(User user_topic) {
        this.user_topic = user_topic;
    }

    public List<Post> getListPost() {
        return listPost;
    }

    public void setListPost(List<Post> listPost) {
        this.listPost = listPost;
    }
}
