package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "User")
public class User {
    @Id
    @Column(name = "Userid")
    private String userid;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Email")
    private String email;

    @Column(name = "Fullname")
    private String fullname;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Address")
    private String address;

    @Column(name = "Gender")
    private String gender;

    @Column(name = "Birthday")
    private Date birthday;

    @Column(name = "Image")
    private String image;

    @Column(name = "Createday")
    private LocalDateTime createday;

    @ManyToOne
    @JoinColumn(name = "isbanid", nullable = false, referencedColumnName = "isbanid")
    private IsBan isban;

    @Column(name = "Type")
    private String type;

    @Column(name = "Mssv")
    private String mssv;

    @OneToOne
    @JoinColumn(name = "Roleid")
    private Role role;

    @Column(name = "Reputation")
    private int reputation;

    @Column(name = "Status")
    private int status;

    @OneToMany(mappedBy = "user_post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> listPost;

    @OneToMany(mappedBy = "user_group", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Group> listGroup;

    @OneToMany(mappedBy = "user_view", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ViewPost> listViewPost;

    @OneToMany(mappedBy = "user_notice", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Notice> listNotice;

    @OneToOne(mappedBy = "user_refresh", cascade = CascadeType.ALL)
    @JsonIgnore
    private RefreshToken refreshToken;
    @OneToMany(mappedBy = "user_follow", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Follow> follow;

    @OneToMany(mappedBy = "user_follower", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Follow> follower;

    @OneToMany(mappedBy = "user_message", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Message> message;

    @OneToMany(mappedBy = "user_detailgroupmessage", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DetailGroup_Message> user_detailgroupmessage;

    @OneToMany(mappedBy = "user_content", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Content_Message> content_messages;

    @OneToMany(mappedBy = "user_contentgroup", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Content_GroupMessage> user_contentgroup;

    @OneToMany(mappedBy = "user_report", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Report> report;

    @OneToMany(mappedBy = "user_reporter", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Report> reporter;

    @OneToMany(mappedBy = "user_comment", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> listComment;

    @OneToMany(mappedBy = "user_bookmark", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bookmark> listBookmark;

    @OneToMany(mappedBy = "user_postlike", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post_Like> listPostlike;

    @OneToMany(mappedBy = "user_usergroup", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User_Group> listUsergroup;

    @OneToMany(mappedBy = "user_commentlike", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment_Like> listCommentlike;

    public User(String userid, String username, String fullname, String image, Role role) {
        this.userid = userid;
        this.username = username;
        this.fullname = fullname;
        this.image = image;
        this.role = role;
    }


    public User() {
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public String getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreateday() {
        return createday;
    }

    public void setCreateday(LocalDateTime createday) {
        this.createday = createday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public IsBan getIsban() {
        return isban;
    }

    public void setIsban(IsBan isban) {
        this.isban = isban;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Post> getListPost() {
        return listPost;
    }

    public void setListPost(List<Post> listPost) {
        this.listPost = listPost;
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

    public List<Comment_Like> getListCommentlike() {
        return listCommentlike;
    }

    public void setListCommentlike(List<Comment_Like> listCommentlike) {
        this.listCommentlike = listCommentlike;
    }
}
