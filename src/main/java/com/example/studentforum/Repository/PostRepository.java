package com.example.studentforum.Repository;

import com.example.studentforum.Model.Notice;
import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT u FROM Post u WHERE (u.postid = ?1) ")
    Post getPostById(int Id);


    @Query("SELECT u FROM Post u WHERE YEAR(u.createday)=?1 and u.ishide=0")
    List<Post> statisticPost(int year);

    @Query("SELECT u FROM Post u")
    List<Post> getPost(Pageable pageable);

    @Query("SELECT u FROM Post u WHERE  LOWER(u.title) LIKE LOWER(CONCAT('%', :keyword, '%')) or LOWER(u.content) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY u.postid DESC")
    List<Post> getPostByKeyword(String keyword);

    @Query("SELECT u FROM Post u WHERE  (u.user_post.userid = ?1) ORDER BY u.postid DESC")
    List<Post> getPostByUserid(String userid);

    @Query("SELECT u FROM Post u WHERE  (u.group_post.groupid = ?1) ORDER BY u.postid DESC")
    List<Post> getPostinGroup(int groupid);

    @Modifying
    @Transactional
    @Query("UPDATE Post u SET u.content = :#{#post.content}, u.title=:#{#post.title}," +
            " u.updateday=:#{#post.updateday}" +
            "  WHERE u.postid = :#{#post.postid}")
    void updatepost(@Param("post") Post post);

    @Modifying
    @Transactional
    @Query("UPDATE Post u SET u.totalread = :#{#post.totalread}  WHERE u.postid = :#{#post.postid}")
    void updateviewpost(@Param("post") Post post);

    @Modifying
    @Transactional
    @Query("UPDATE Post u SET u.ishide = :#{#post.ishide} WHERE u.postid = :#{#post.postid}")
    void hidepost(@Param("post") Post post);
}
