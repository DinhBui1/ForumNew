package com.example.studentforum.Repository;

import com.example.studentforum.Model.Comment_Like;
import com.example.studentforum.Model.Post_Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Post_LikeRepository extends JpaRepository<Post_Like,Integer> {
    @Query("SELECT u FROM Post_Like u WHERE (u.post_postlike.postid = ?1) ")
    List<Post_Like> getPostLikeByPostId(int Id);

    @Query("SELECT u FROM Post_Like u WHERE (u.post_postlike.postid = ?1 and u.icon_postlike.iconid=1 ) ")
    List<Post_Like> getPostLikeByPostIdandLike(int Id);
    @Query("SELECT u FROM Post_Like u WHERE (u.user_postlike.userid = ?1  ) ")
    List<Post_Like> getPostLikeByUserid(String userid);
    @Query("SELECT u FROM Post_Like u WHERE (u.post_postlike.postid = ?1 and u.user_postlike.userid=?2 ) ")
    Post_Like getPostLikeByPostIdandUserid(int postid, String userid);;
    @Query("SELECT u FROM Post_Like u WHERE (u.post_postlike.postid = ?1 and u.icon_postlike.iconid=2 ) ")
    List<Post_Like> getPostLikeByPostIdandDisLike(int Id);
    @Query("SELECT u FROM Post_Like u WHERE (u.user_postlike.userid= ?1 and u.post_postlike.postid = ?2) ")
    List<Post_Like> getPostLikeByPostIdandUserid(String userid, int postid);
    @Query("SELECT u FROM Post_Like u WHERE (u.user_postlike.userid= ?1 and u.post_postlike.postid = ?2 and u.icon_postlike.iconid=?3 ) ")
    Post_Like getPostLikeByPostIdandLikeandUserid(String userid, int postid, int iconid);

}
