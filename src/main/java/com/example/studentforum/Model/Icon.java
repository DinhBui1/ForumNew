package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Icon")
public class Icon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Iconid")
    private int iconid;
    @Column(name = "Iconame")
    private String iconname;
    @Column(name = "Iconimage")
    private String iconimage;

    @OneToMany(mappedBy = "icon_postlike", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post_Like> listPostlike;

    @OneToMany(mappedBy = "icon_commentlike", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment_Like> listCommentlike;
    

    @OneToMany(mappedBy = "icon_iconcontentgroupmessage", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ContentGroupMessage_Icon> icon_iconcontentgroupmessage;

    public Icon(int iconid, String iconname, String iconimage) {
        this.iconid = iconid;
        this.iconname = iconname;
        this.iconimage = iconimage;
    }

    public Icon() {
    }

    public int getIconid() {
        return iconid;
    }

    public void setIconid(int iconid) {
        this.iconid = iconid;
    }

    public String getIconname() {
        return iconname;
    }

    public void setIconname(String iconname) {
        this.iconname = iconname;
    }

    public String getIconimage() {
        return iconimage;
    }

    public void setIconimage(String iconimage) {
        this.iconimage = iconimage;
    }

    public List<Post_Like> getListPostlike() {
        return listPostlike;
    }

    public void setListPostlike(List<Post_Like> listPostlike) {
        this.listPostlike = listPostlike;
    }

    public List<Comment_Like> getListCommentlike() {
        return listCommentlike;
    }

    public void setListCommentlike(List<Comment_Like> listCommentlike) {
        this.listCommentlike = listCommentlike;
    }
}
