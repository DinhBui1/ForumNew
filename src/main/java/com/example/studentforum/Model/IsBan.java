package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "IsBan")
public class IsBan {
    @Id
    @Column(name = "Isbanid")
    private int isbanid;

    @Column(name = "NameBan")
    private String nameban;

    @Column(name = "Description")
    private String description;

    public int getIsbanid() {
        return isbanid;
    }

    public void setIsbanid(int isbanid) {
        this.isbanid = isbanid;
    }

    public String getNameban() {
        return nameban;
    }

    public void setNameban(String nameban) {
        this.nameban = nameban;
    }

    @OneToMany(mappedBy = "isban",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> listUser;
    public String getDescription() {
        return description;
    }

    public IsBan() {
    }

    public IsBan(int isbanid) {
        this.isbanid = isbanid;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
