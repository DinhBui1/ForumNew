package com.example.studentforum.Service;

import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private Comment_LikeRepository comment_likeRepository;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private NoticeRepository noticeRepository;

    public List<Comment> getAllComment(int limit, int pacing) {
        int offset = limit * (pacing - 1);
        return commentRepository.getComment(limit, offset);
    }

    public String updateCommentById(Comment comment) {
        Comment c = commentRepository.getCommentById(comment.getCommentid());
        if (c.getUser_comment().getIsban().getIsbanid() != 0) {
            return "User has been exit ban list";
        }
        c.setContent(comment.getContent());
        c.setUpdateday(LocalDateTime.now());
        commentRepository.updatecomment(c);
        return "Update Comment Success";
    }

    public String createComment(Comment comment, String userid, int postid) {
        comment.setCreateday(LocalDateTime.now());
        User u = userRepository.getUserById(userid);
        if (u.getIsban().getIsbanid() != 0) {
            return "User has been exit ban list";
        }
        Post p = postRepository.getPostById(postid);
        if (p.getRequiredreputation() > u.getReputation() && p.getUser_post().getUserid() != u.getUserid()) {
            return "User isn't enought reputation to comment";
        }
        comment.setUser_comment(u);
        comment.setPost_comment(p);
        comment.setCreateday(LocalDateTime.now());
        commentRepository.save(comment);
        if (u.getUserid() != p.getUser_post().getUserid()) {
            Notice n = noticeRepository.findNoticeByUserIdandTypeandSubject(p.getUser_post().getUserid(), 7, p.getPostid());
            if (n == null) {
                String content = "<b>" + u.getFullname() + "</b> đã bình luận bài viết <b>" + p.getTitle() + "</b>";
                Notice nt = noticeService.createNotice(p.getUser_post().getUserid(), content, 7, p.getPostid());
            } else {
                String content = "<b>" + u.getFullname() + "</b> và những người khác đã bình luận bài viết <b>" + p.getTitle() + "</b>";
                n.setContent(content);
                noticeRepository.updatenotice(n);
            }
        }
//        String destination = String.format("/notification/%s", p.getUser_post().getUserid());
//        simpMessagingTemplate.convertAndSendToUser(p.getUser_post().getUserid(),destination,content);
        return "Create Comment Success";
    }

    public String createCommentinComment(Comment comment, String userid, int comment_parentid) {
        User u = userRepository.getUserById(userid);
        if (u.getIsban().getIsbanid() != 0) {
            return "User has been exit ban list";
        }
        Comment cp = commentRepository.getCommentById(comment_parentid);
        Post p = checkCommentParent(cp);
        if (p.getRequiredreputation() > u.getReputation() && p.getUser_post().getUserid() != u.getUserid()) {
            return "User isn't enought reputation to comment";
        }
        comment.setUser_comment(u);
        comment.setComment_comment(cp);
        comment.setCreateday(LocalDateTime.now());
        comment.setPost_comment(cp.getPost_comment());
        commentRepository.save(comment);
        if (u.getUserid() != cp.getUser_comment().getUserid()) {
            Notice n = noticeRepository.findNoticeByUserIdandTypeandSubject(cp.getUser_comment().getUserid(), 8, cp.getCommentid());
            if (n == null) {
                String content = "<b>" + u.getFullname() + "</b> đã trả lời bình luận <b>" + cp.getContent() + "</b>";
                Notice nt = noticeService.createNotice(cp.getUser_comment().getUserid(), content, 8, cp.getCommentid());
            } else {
                String content = "<b>" + u.getFullname() + "</b> và những người khác đã trả lời bình luận <b>" + cp.getContent() + "</b>";
                n.setContent(content);
                noticeRepository.updatenotice(n);
            }
        }
        return "Create Comment in Comment Success";
    }

    public Post checkCommentParent(Comment c) {
        if (c.getComment_comment() != null) {
            return checkCommentParent(c.getComment_comment());
        } else {
            return c.getPost_comment();
        }
    }

    public void deleteCommentChild(int commentid, int postid) {
        List<Comment> comments = commentRepository.getCommentByCommentParentId(commentid, postid);
        ArrayList<Integer> com = new ArrayList<>();
        com.add(commentid);
        if (comments.size() != 0) {
            for (Comment comment : comments) {
                deleteCommentChild(comment.getCommentid(), postid);
            }
        }
        for (int i = com.size() - 1; i >= 0; i--) {
            int x = com.get(i);
            Comment c = commentRepository.getCommentById(x);
            List<Comment_Like> commentLikes = comment_likeRepository.getCommentLikeByCommentId(commentid);
            if (commentLikes != null) {
                for (Comment_Like cls : commentLikes) {
                    comment_likeRepository.delete(cls);
                }
            }
            commentRepository.delete(c);
        }
    }


    public String deleteComment(int commentid) {
        try {
            Comment c = commentRepository.getCommentById(commentid);
            deleteCommentChild(commentid, c.getPost_comment().getPostid());
            return "Delete Success";
        } catch (Exception e) {
            return "Delete Failure";
        }
    }

    public List<Comment> getAllCommentByPostId(int postid) {
        return commentRepository.getCommentByPostId(postid);

    }

    public List<Comment> getAllCommentByCommentParentId(int commentparentid, int postid) {
        return commentRepository.getCommentByCommentParentId(commentparentid, postid);

    }

    public int checkCommentinComment(int commentid) {
        Comment c = commentRepository.getCommentById(commentid);
        if (c.getComment_comment() != null) {
            return 1;
        }
        return 0;
    }
}
