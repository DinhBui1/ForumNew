package com.example.studentforum.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "Refresh")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int refreshid;
    @OneToOne
    @JoinColumn(name = "user_refresh",nullable = false,referencedColumnName = "userid")
    private User user_refresh;
    @Column(name = "Token")
    private String  token;
    @Column(name = "Created")
    private Date created;

    public RefreshToken() {
    }

    public int getRefreshid() {
        return refreshid;
    }

    public void setRefreshid(int refreshid) {
        this.refreshid = refreshid;
    }

    public User getUser_refresh() {
        return user_refresh;
    }

    public void setUser_refresh(User user_refresh) {
        this.user_refresh = user_refresh;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
