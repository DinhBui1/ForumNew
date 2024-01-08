package com.example.studentforum.Service;

import com.example.studentforum.Model.Follow;
import com.example.studentforum.Model.Notice;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.FollowRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserService userService;

    public String createFollow(String userid, String followerid){
        Follow f = new Follow();
        User user = userRepository.getUserById(userid);
        User follower = userRepository.getUserById(followerid);
        if(user == null || follower == null){
            return "User or Follower not exit";
        }
        Follow follow = followRepository.getFollowByUserIdandFollowerId(userid,followerid);
        if(follow!=null){
            return "Follow has been exit";
        }
        f.setCreateday(LocalDateTime.now());
        f.setUser_follow(user);
        f.setUser_follower(follower);
        followRepository.save(f);
        String  content = "<b>"+follower.getFullname() + "</b> đã theo dõi bạn";
        Notice n = noticeService.createNotice(userid,content,6,0);
        userService.updateReputation(userid,20);
        return "Create Follower Success";
    }
    public String deleteFollow(String userid, String followerid){
        Follow f = followRepository.getFollowByUserIdandFollowerId(userid,followerid);
        if(f == null){
            return "Follow not exit";
        }
        followRepository.deleteByFollowerId(f.getFollowid());
        userService.updateReputation(userid,-20);
        return "Delete Follow Success";
    }

    //Lấy ra danh sách follower của user
    public List<User> getallFollowerByUserId(String userid){
        List<Follow> follows = followRepository.getFollowerByUserId(userid);
        List<User> users = new ArrayList<>();
        for(Follow f :follows){
            User u = userRepository.getUserById(f.getUser_follower().getUserid());
            users.add(u);
        }
        return users;
    }

    //Lấy ra danh sách user đã follow
    public List<User> getallUserIdByFollower(String followerid){
        List<Follow> follows = followRepository.getUserIdByFollowerid(followerid);
        List<User> users = new ArrayList<>();
        for(Follow f :follows){
            User u = userRepository.getUserById(f.getUser_follow().getUserid());
            users.add(u);
        }
        return users;
    }
}
