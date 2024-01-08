package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Roleid")
    private int roleid;
    @Column(name = "Rolename")
    private String rolename;
    @Column(name = "Path")
    private String path;

    @OneToOne(mappedBy = "role")
    @JsonIgnore
    private User user;

    public Role(int roleid, String rolename, String path, User user) {
        this.roleid = roleid;
        this.rolename = rolename;
        this.path = path;
        this.user = user;
    }
    public Role( int roleid)
    {
        this.roleid=roleid;
    }

    public Role() {
        this.roleid = 1;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
