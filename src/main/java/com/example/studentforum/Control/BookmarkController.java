package com.example.studentforum.Control;

import com.example.studentforum.Model.Bookmark;
import com.example.studentforum.Service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookmarkController {
    @Autowired
     private BookmarkService bookmarkService;

    @MutationMapping
    public Bookmark create_bookmark(@Argument("userid") String userid,@Argument("postid") int postid){
        return bookmarkService.createBookmark(postid,userid);
    }
    @MutationMapping
    public String delete_bookmark(@Argument("userid") String userid,@Argument("postid") int postid){
        return bookmarkService.deleteBookmark(postid,userid);
    }
    @QueryMapping
    public List<Bookmark> find_all_bookmark_by_userid(@Argument("userid") String userid){
        return bookmarkService.getallBookmarkbyUserid(userid);
    }
}
