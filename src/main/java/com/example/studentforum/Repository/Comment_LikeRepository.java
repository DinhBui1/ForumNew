package com.example.studentforum.Repository;

import com.example.studentforum.Model.Comment;
import com.example.studentforum.Model.Comment_Like;
import com.example.studentforum.Model.Post_Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Comment_LikeRepository extends JpaRepository<Comment_Like,Integer> {
    @Query("SELECT u FROM Comment_Like u WHERE (u.comment_commentlike.commentid = ?1) ")
    List<Comment_Like> getCommentLikeByCommentId(int Id);
    @Query("SELECT u FROM Comment_Like u WHERE (u.comment_commentlike.commentid = ?1 and u.icon_commentlike.iconid=1 ) ")
    List<Comment_Like> getCommentLikeByCommentIdandLike(int Id);
    @Query("SELECT u FROM Comment_Like u WHERE (u.user_commentlike.userid=?1 ) ")
    List<Comment_Like> getCommentLikeBUserid(String userid);
    @Query("SELECT u FROM Comment_Like u WHERE (u.comment_commentlike.commentid = ?1 and u.icon_commentlike.iconid=2 ) ")
    List<Comment_Like> getCommentDisLikeByCommentIdandLike(int Id);
    @Query("SELECT u FROM Comment_Like u WHERE (u.user_commentlike.userid = ?1 and u.comment_commentlike.commentid = ?2 ) ")
    List<Comment_Like> getCommentIconByCommentIdandUserid(String userid, int commentid);
    @Query("SELECT u FROM Comment_Like u WHERE (u.user_commentlike.userid = ?1 and u.comment_commentlike.commentid = ?2 and u.icon_commentlike.iconid=?3 ) ")
    Comment_Like getCommentIconByCommentIdandLikeandUserid(String userid, int commentid, int iconid);
    @Query("SELECT u FROM Comment_Like u WHERE (u.comment_commentlike.commentid = ?1 and u.user_commentlike.userid=?2 ) ")
    Comment_Like getCommentLikeByCommentIdandUserid(int commentid, String userid);
}
