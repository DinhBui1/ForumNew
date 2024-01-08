package com.example.studentforum.Service;

import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class Comment_LikeService {
    @Autowired
    private Comment_LikeRepository comment_likeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IconRepository iconRepository;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private UserService userService;
    @Autowired
    private NoticeRepository noticeRepository;
    public int CountTotalLikeByComment(int commentid){
        List<Comment_Like> cls = comment_likeRepository.getCommentLikeByCommentIdandLike(commentid);
        return cls.size();
    }

    public  List<Comment_Like> getListCommentLikeByCommentid(int commentid){
        List<Comment_Like> cls = comment_likeRepository.getCommentLikeByCommentIdandLike(commentid);
        return cls;
    }
    public int CountTotalDisLikeByComment(int commentid){
        List<Comment_Like> cls = comment_likeRepository.getCommentDisLikeByCommentIdandLike(commentid);
        return cls.size();
    }
    public  List<Comment_Like> getListCommentDisLikeByCommentid(int commentid){
        List<Comment_Like> cls = comment_likeRepository.getCommentDisLikeByCommentIdandLike(commentid);
        return cls;
    }
    public String createIconForCommentLike(String userid, int commentid, int iconid){
        User u =userRepository.getUserById(userid);
        Comment c = commentRepository.getCommentById(commentid);
        Icon i = iconRepository.getIconById(iconid);
        if(u==null || c == null || i==null){
            return "Not Find";
        }
        if(c.getPost_comment().getRequiredreputation()>u.getReputation() && c.getUser_comment().getUserid()!=u.getUserid()){
            return "User isn't enought reputation to perform the operation";
        }
        Comment_Like commentLike =comment_likeRepository.getCommentIconByCommentIdandLikeandUserid(userid,commentid,iconid);
        if(commentLike!=null){
            return "Comment_Like has been exit";
        }
        List<Comment_Like> commentLikes = comment_likeRepository.getCommentIconByCommentIdandUserid(userid,commentid);
        for (Comment_Like cls :commentLikes){
            if(cls.getIcon_commentlike().getIconid() ==1){
                userService.updateReputation(cls.getComment_commentlike().getUser_comment().getUserid(),-10);
            }
            else {
                userService.updateReputation(cls.getComment_commentlike().getUser_comment().getUserid(),10);
            }
            comment_likeRepository.delete(cls);
        }
        Comment_Like ck = new Comment_Like();
        ck.setComment_commentlike(c);
        ck.setUser_commentlike(u);
        ck.setCreateday(LocalDateTime.now());
        ck.setIcon_commentlike(i);
        comment_likeRepository.save(ck);
        if(u.getUserid()!=c.getUser_comment().getUserid()){
            if(iconid==1){
                Notice nt = noticeRepository.findNoticeByUserIdandTypeandSubject(c.getUser_comment().getUserid(),4,c.getCommentid());
                if(nt==null) {
                    String content = "Bình luận của bạn đã đươc like trong bài viết " + c.getPost_comment().getTitle();
                    Notice n = noticeService.createNotice(c.getUser_comment().getUserid(), content, 4, c.getCommentid());
                }
                else {
                    nt.setIsseen(0);
                    noticeRepository.updateisseen(nt);
                    noticeRepository.updatenotice(nt);
                }
            }
            else {
                Notice nt = noticeRepository.findNoticeByUserIdandTypeandSubject(c.getUser_comment().getUserid(),5,c.getCommentid());
                if(nt==null) {
                    String content = "Bình luận của bạn đã bị dislike trong bài viết " + c.getPost_comment().getTitle();
                    Notice n = noticeService.createNotice(c.getUser_comment().getUserid(), content, 5, c.getCommentid());
                }
                else {
                    nt.setIsseen(0);
                    noticeRepository.updateisseen(nt);
                    noticeRepository.updatenotice(nt);
                }
            }
            if(iconid ==1){
                userService.updateReputation(ck.getComment_commentlike().getUser_comment().getUserid(),10);
            }
            else {
                userService.updateReputation(ck.getComment_commentlike().getUser_comment().getUserid(),-10);
            }
        }
        return  "Create Comment_Like Success";
    }
    public String deleteIconForCommentLike(String userid, int commentid, int iconid){
        Comment_Like cl = comment_likeRepository.getCommentIconByCommentIdandLikeandUserid(userid,commentid,iconid);
        if(cl==null){
            return "Comment_Like Not Exit";
        }
        if(iconid ==1){
            userService.updateReputation(cl.getComment_commentlike().getUser_comment().getUserid(),-10);
        }
        else {
            userService.updateReputation(cl.getComment_commentlike().getUser_comment().getUserid(),10);
        }
        comment_likeRepository.delete(cl);
        return "Delete Comment_Like Success";
    }
    public Comment_Like getCommentLikebycommentIdandUserid(int commentid, String userid){
        return comment_likeRepository.getCommentLikeByCommentIdandUserid(commentid, userid);
    }
    public List<Comment_Like> getCommentLikebyUserid( String userid){
        return comment_likeRepository.getCommentLikeBUserid(userid);
    }
}
