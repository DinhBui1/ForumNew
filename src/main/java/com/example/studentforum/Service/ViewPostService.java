package com.example.studentforum.Service;

import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.User;
import com.example.studentforum.Model.ViewPost;
import com.example.studentforum.Repository.PostRepository;
import com.example.studentforum.Repository.UserRepository;
import com.example.studentforum.Repository.ViewPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ViewPostService {
    @Autowired
    private ViewPostRepository viewPostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    public int createViewPost(String userid, int postid){
        User u = userRepository.getUserById(userid);
        Post p = postRepository.getPostById(postid);
        ViewPost viewcheck =viewPostRepository.getViewPostByPostIdandUserid(postid,userid);
        if(viewcheck!= null){
            return 0;
        }
        if(u==null || p ==null){
            return 0;
        }
        ViewPost v = new ViewPost();
        v.setCreateday(LocalDateTime.now());
        v.setUser_view(u);
        v.setPost_view(p);
        viewPostRepository.save(v);
        return 1;
    }

    public String deleteViewPostbyPostid(int postid){
        List<ViewPost> viewPosts =viewPostRepository.getViewPostByPostId(postid);
        for(ViewPost v :viewPosts){
            viewPostRepository.delete(v);
        }
        return "Delete ViewPost Success";
    }

    public int totalViewPostByPostid(int postid){
        List<ViewPost> viewPosts =viewPostRepository.getViewPostByPostId(postid);
        return viewPosts.size();
    }

}
