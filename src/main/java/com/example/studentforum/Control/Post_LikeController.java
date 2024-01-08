package com.example.studentforum.Control;

import com.example.studentforum.Model.Post_Like;
import com.example.studentforum.Service.Post_LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class Post_LikeController {
    @Autowired
    private Post_LikeService post_likeService;

    @QueryMapping
    public int find_all_likepost_by_postid(@Argument int postid){
        return post_likeService.CountTotalLikeByPost(postid);
    }
    @QueryMapping
    public  int find_all_dislikepost_by_postid(@Argument int postid){
        return post_likeService.CountTotalDisLikeByPost(postid);
    }
    @MutationMapping
    public String create_icon_for_postlike(@Argument("userid") String userid,@Argument("postid") int postid,@Argument("iconid") int iconid){
        return post_likeService.createIconForPostLike(userid,postid,iconid);
    }
    @MutationMapping
    public String delete_icon_for_postlike(@Argument("userid") String userid,@Argument("postid") int postid,@Argument("iconid") int iconid){
        return post_likeService.deleteIconForPostLike(userid,postid,iconid);
    }
    @QueryMapping
    public Post_Like find_postlike_by_postid_and_userid(@Argument int postid,@Argument String userid){
        return post_likeService.getPost_LikeByPostIdnadUserid(postid,userid);
    }
    @QueryMapping
    public List<Post_Like> find_postlike_byuserid(@Argument String userid){
        return post_likeService.getPost_LikeByUserid(userid);
    }
}
