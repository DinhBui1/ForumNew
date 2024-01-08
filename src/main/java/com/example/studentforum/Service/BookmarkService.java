package com.example.studentforum.Service;

import com.example.studentforum.Model.Bookmark;
import com.example.studentforum.Model.Notice;
import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.BookmarkRepository;
import com.example.studentforum.Repository.NoticeRepository;
import com.example.studentforum.Repository.PostRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoticeService noticeService;

    public Bookmark createBookmark(int postid, String userid){
        Bookmark bm = new Bookmark();
        User u = userRepository.getUserById(userid);
        Post p = postRepository.getPostById(postid);
        if(u==null || p == null){
            return null;
        }
        Bookmark bmcheck= bookmarkRepository.findBookmarkByUserIdandPostId(userid,postid);
        if(bmcheck!=null){
            return null;
        }
        bm.setPost_bookmark(p);
        bm.setUser_bookmark(u);
        bm.setCreateday(LocalDateTime.now());
        bookmarkRepository.save(bm);
        return bm;
    }

    public String deleteBookmark(int postid, String userid){
        Bookmark bm =bookmarkRepository.findBookmarkByUserIdandPostId(userid,postid);
        if(bm==null ){
            return "Bookmark Not Exit";
        }
        bookmarkRepository.delete(bm);
        return "Delete Bookmark Success";
    }

    public List<Bookmark> getallBookmarkbyUserid(String userid){
        User u =userRepository.getUserById(userid);
        if(u==null){
            return  null;
        }
        List<Bookmark> bookmarks = bookmarkRepository.findallBookmarkByUserId(userid);
        return bookmarks;
    }
}
