package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "Notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Noticeid")
    private int noiticeid;

    @ManyToOne
    @JoinColumn(name = "userid",nullable = false,referencedColumnName = "userid")
    private User user_notice;

    @Column(name = "Content",columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "Createday")
    private LocalDateTime createday;

    @Column(name = "Isseen")
    private int isseen;

    @Column(name = "Type")
    private int type;

    @Column(name = "Subjectid")
    private int subjectid;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }

    public int getIsseen() {
        return isseen;
    }

    public void setIsseen(int isseen) {
        this.isseen = isseen;
    }

    public int getNoiticeid() {
        return noiticeid;
    }

    public void setNoiticeid(int noiticeid) {
        this.noiticeid = noiticeid;
    }

    public User getUser_notice() {
        return user_notice;
    }

    public void setUser_notice(User user_notice) {
        this.user_notice = user_notice;
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
}
