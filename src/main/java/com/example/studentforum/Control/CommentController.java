package com.example.studentforum.Control;

import com.example.studentforum.Model.Comment;
import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.User;
import com.example.studentforum.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @QueryMapping
    public List<Comment> comment(@Argument int limit, @Argument int pacing) {
        return commentService.getAllComment(limit, pacing);
    }

    @MutationMapping
    public String update_comment_by_pk(@Argument("comment") Comment comment) {
        return commentService.updateCommentById(comment);
    }

    @MutationMapping
    public Comment create_comment(@Argument("comment") Comment comment, @Argument("userid") String userid, @Argument("postid") int postid) {
        return commentService.createComment(comment, userid, postid);
    }

    @MutationMapping
    public Comment create_comment_in_comment(@Argument("comment") Comment comment, @Argument("userid") String userid, @Argument("comment_parentid") int comment_parentid) {
        return commentService.createCommentinComment(comment, userid, comment_parentid);
    }

    @MutationMapping
    public String delete_comment_by_pk(@Argument int commentid) {
        return commentService.deleteComment(commentid);
    }

    @QueryMapping
    public List<Comment> find_all_comment_by_postid(@Argument int postid) {
        return commentService.getAllCommentByPostId(postid);
    }

    @QueryMapping
    public List<Comment> find_all_comment_by_commentparentid(@Argument int commentparentid, @Argument int postid) {
        return commentService.getAllCommentByCommentParentId(commentparentid, postid);
    }

    @QueryMapping
    public int check_comment_in_comment(@Argument int commentid) {
        return commentService.checkCommentinComment(commentid);
    }

}
