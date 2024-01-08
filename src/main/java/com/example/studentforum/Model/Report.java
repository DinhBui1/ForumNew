package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Reportid")
    private int reportid;

    @Column(name = "Reason")
    private String reason;

    @Column(name = "Content")
    private String content;

    @Column(name = "Type")
    private int type;

    @Column(name = "Createday")
    private LocalDateTime createday;

    @ManyToOne
    @JoinColumn(name = "user_report",referencedColumnName = "userid")
    private User user_report;

    @ManyToOne
    @JoinColumn(name = "post_report",referencedColumnName = "postid")
    private Post post_report;

    @ManyToOne
    @JoinColumn(name = "comment_report",referencedColumnName = "commentid")
    private Comment comment_report;

    @ManyToOne
    @JoinColumn(name = "user_reporter",referencedColumnName = "userid")
    private User user_reporter;

    public int getReportid() {
        return reportid;
    }

    public void setReportid(int reportid) {
        this.reportid = reportid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser_report() {
        return user_report;
    }

    public void setUser_report(User user_report) {
        this.user_report = user_report;
    }

    public Post getPost_report() {
        return post_report;
    }

    public void setPost_report(Post post_report) {
        this.post_report = post_report;
    }

    public Comment getComment_report() {
        return comment_report;
    }

    public void setComment_report(Comment comment_report) {
        this.comment_report = comment_report;
    }

    public User getUser_reporter() {
        return user_reporter;
    }

    public void setUser_reporter(User user_reporter) {
        this.user_reporter = user_reporter;
    }

    public LocalDateTime getCreateday() {
        return createday;
    }

    public void setCreateday(LocalDateTime createday) {
        this.createday = createday;
    }
}
