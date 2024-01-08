package com.example.studentforum.Repository;

import com.example.studentforum.Model.Comment;
import com.example.studentforum.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    @Query("SELECT u FROM Comment u WHERE (u.commentid = ?1) ")
    Comment getCommentById(int Id);
    @Query("SELECT u FROM Comment u WHERE (u.post_comment.postid = ?1 and u.comment_comment.commentid is null ) ")
    List<Comment> getCommentByPostId(int Id);
    @Query("SELECT u FROM Comment u WHERE (u.comment_comment.commentid = ?1 and u.post_comment.postid=?2) ")
    List<Comment> getCommentByCommentParentId(int commentid,int postid);
    @Query(value = "SELECT * FROM Comment LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Comment> getComment(@Param("limit") int limit,@Param("offset") int offset);

    @Modifying
    @Transactional
    @Query("UPDATE Comment u SET u.content = :#{#comment.content}, u.updateday=:#{#comment.updateday}" +
            " WHERE u.commentid = :#{#comment.commentid}")
    void updatecomment(@Param("comment") Comment comment);
}
