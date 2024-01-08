package com.example.studentforum.Control;

import com.example.studentforum.Model.Comment_Like;
import com.example.studentforum.Service.Comment_LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class Comment_LikeController {
    @Autowired
    private Comment_LikeService comment_likeService;
    @QueryMapping
    public List<Comment_Like> list_commentlike_by_commentid(@Argument int commentid){
        return comment_likeService.getListCommentLikeByCommentid(commentid);
    }
    @QueryMapping
    public List<Comment_Like> list_commentdislike_by_commentid(@Argument int commentid){
        return comment_likeService.getListCommentDisLikeByCommentid(commentid);
    }
    @QueryMapping
    public int find_all_likecomment_by_commentid(@Argument int commentid){
        return  comment_likeService.CountTotalLikeByComment(commentid);
    }
    @QueryMapping
    public int find_all_dislikecomment_by_commentid(@Argument int commentid){
        return  comment_likeService.CountTotalDisLikeByComment(commentid);
    }
    @MutationMapping
    public String create_icon_for_commentlike(@Argument("userid") String userid,@Argument("commentid") int commentid,@Argument("iconid") int iconid){
        return comment_likeService.createIconForCommentLike(userid,commentid,iconid);
    }
    @MutationMapping
    public String delete_icon_for_commentlike(@Argument("userid") String userid,@Argument("commentid") int commentid,@Argument("iconid") int iconid){
        return comment_likeService.deleteIconForCommentLike(userid,commentid,iconid);
    }
    @QueryMapping
    public Comment_Like find_commentlike_by_commentid_and_userid(@Argument int commentid,@Argument String userid){
        return comment_likeService.getCommentLikebycommentIdandUserid(commentid,userid);
    }
    @QueryMapping
    public List<Comment_Like> find_commentlike_byuserid(@Argument String userid){
        return comment_likeService.getCommentLikebyUserid(userid);
    }
}
