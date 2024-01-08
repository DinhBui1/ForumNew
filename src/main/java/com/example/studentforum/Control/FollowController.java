package com.example.studentforum.Control;

import com.example.studentforum.Model.User;
import com.example.studentforum.Service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FollowController {
    @Autowired
    private FollowService followService;

    @MutationMapping
    public String create_follow(@Argument("userid") String userid,@Argument("followerid") String followerid){
        return followService.createFollow(userid,followerid);
    }

    @MutationMapping
    public String delete_follow(@Argument("userid") String userid,@Argument("followerid") String followerid){
        return followService.deleteFollow(userid,followerid);
    }

    @QueryMapping
    public List<User> get_all_follower_by_user(@Argument("userid") String userid){
        return followService.getallFollowerByUserId(userid);
    }

    @QueryMapping
    public List<User> get_all_user_by_follower(@Argument("followerid") String followerid){
        return followService.getallUserIdByFollower(followerid);
    }
}
