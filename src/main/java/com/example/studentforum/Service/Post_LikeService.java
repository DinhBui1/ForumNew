package com.example.studentforum.Service;

import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.sax.SAXResult;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class Post_LikeService {
    @Autowired
    private Post_LikeRepository post_likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoticeRepository noticeRepository;

    public int CountTotalLikeByPost(int postid) {
        List<Post_Like> pls = post_likeRepository.getPostLikeByPostIdandLike(postid);
        return pls.size();
    }

    public int CountTotalDisLikeByPost(int postid) {
        List<Post_Like> pls = post_likeRepository.getPostLikeByPostIdandDisLike(postid);
        return pls.size();
    }

    public String createIconForPostLike(String userid, int postid, int iconid) {
        User u = userRepository.getUserById(userid);
        Post p = postRepository.getPostById(postid);
        Icon i = iconRepository.getIconById(iconid);
        if (u == null || p == null || i == null) {
            return "Not Find";
        }
        if (p.getRequiredreputation() > u.getReputation() && p.getUser_post().getUserid() != u.getUserid()) {
            return "User isn't enought reputation to perform the operation ";
        }
        Post_Like postLike = post_likeRepository.getPostLikeByPostIdandLikeandUserid(userid, postid, iconid);
        if (postLike != null) {
            return "Post_Like has been exit";
        }
        List<Post_Like> postLikes = post_likeRepository.getPostLikeByPostIdandUserid(userid, postid);
        for (Post_Like pks : postLikes) {
            if (pks.getIcon_postlike().getIconid() == 1) {
                userService.updateReputation(pks.getPost_postlike().getUser_post().getUserid(), -10);
            } else {
                userService.updateReputation(pks.getPost_postlike().getUser_post().getUserid(), 10);
            }
            post_likeRepository.delete(pks);
        }
        Post_Like pk = new Post_Like();
        pk.setPost_postlike(p);
        pk.setUser_postlike(u);
        pk.setCreateday(LocalDateTime.now());
        pk.setIcon_postlike(i);
        post_likeRepository.save(pk);
        if (u.getUserid() != p.getUser_post().getUserid()) {
            if (iconid == 1) {
                Notice nt = noticeRepository.findNoticeByUserIdandTypeandSubject(p.getUser_post().getUserid(), 2, p.getPostid());
                if (nt == null) {
                    String content = "Bài viết <b>" + p.getTitle() + "</b> đã được like";
                    Notice n = noticeService.createNotice(p.getUser_post().getUserid(), content, 2, p.getPostid());
                } else {
                    nt.setIsseen(0);
                    noticeRepository.updateisseen(nt);
                    noticeRepository.updatenotice(nt);
                }
            } else {
                Notice nt = noticeRepository.findNoticeByUserIdandTypeandSubject(p.getUser_post().getUserid(), 3, p.getPostid());
                if (nt == null) {
                    String content = "Bài viết <b>" + p.getTitle() + "</b> đã bị dislike";
                    Notice n = noticeService.createNotice(p.getUser_post().getUserid(), content, 3, p.getPostid());
                } else {
                    nt.setIsseen(0);
                    noticeRepository.updateisseen(nt);
                    noticeRepository.updatenotice(nt);
                }
            }
            if (iconid == 1) {
                userService.updateReputation(pk.getPost_postlike().getUser_post().getUserid(), 10);
            } else {
                userService.updateReputation(pk.getPost_postlike().getUser_post().getUserid(), -10);
            }
        }

        return "Create Post_Like Success";
    }

    public String deleteIconForPostLike(String userid, int postid, int iconid) {
        Post_Like pk = post_likeRepository.getPostLikeByPostIdandLikeandUserid(userid, postid, iconid);
        if (pk == null) {
            return "Post_Like Not Exit";
        }
        if (pk.getPost_postlike().getUser_post().getUserid().equals(userid) == false) {

            if (iconid == 1) {
                userService.updateReputation(pk.getPost_postlike().getUser_post().getUserid(), -10);
            } else {
                userService.updateReputation(pk.getPost_postlike().getUser_post().getUserid(), 10);
            }
        }
        post_likeRepository.delete(pk);

        return "Delete Post_Like Success";
    }

    public Post_Like getPost_LikeByPostIdnadUserid(int postid, String userid) {
        return post_likeRepository.getPostLikeByPostIdandUserid(postid, userid);
    }

    public List<Post_Like> getPost_LikeByUserid(String userid) {
        return post_likeRepository.getPostLikeByUserid(userid);
    }
}
