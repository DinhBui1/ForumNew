package com.example.studentforum.Repository;

import com.example.studentforum.Model.Post_Like;
import com.example.studentforum.Model.ViewPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewPostRepository extends JpaRepository<ViewPost, Integer> {
    @Query("SELECT u FROM ViewPost u WHERE (u.post_view.postid = ?1) ")
    List<ViewPost> getViewPostByPostId(int Id);

    @Query("SELECT u FROM ViewPost u WHERE (u.post_view.postid = ?1 and u.user_view.userid = ?2) ")
    ViewPost getViewPostByPostIdandUserid(int postid, String userid);

    @Query("SELECT u FROM ViewPost u WHERE (u.user_view.userid = ?1) ")
    List<ViewPost> getViewPostByUserId(String Id);
}
