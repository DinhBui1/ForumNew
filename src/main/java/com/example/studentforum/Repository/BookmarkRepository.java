package com.example.studentforum.Repository;

import com.example.studentforum.Model.Bookmark;
import com.example.studentforum.Model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Integer> {
    @Query("select u from Bookmark u where u.user_bookmark.userid=?1 and u.post_bookmark.postid=?2")
    Bookmark findBookmarkByUserIdandPostId(String userid,int postid);
    @Query("select u from Bookmark u where u.user_bookmark.userid=?1")
    List<Bookmark> findallBookmarkByUserId(String userid);
}
