package com.example.studentforum.Repository;

import com.example.studentforum.Model.Bookmark;
import com.example.studentforum.Model.Comment;
import com.example.studentforum.Model.Notice;
import com.example.studentforum.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Integer> {
    @Query("select u from Notice u where u.noiticeid=?1")
    Notice findNoticeByNoiticeid( int noticeid);
    @Query("select u from Notice u where u.user_notice.userid=?1 ORDER BY u.noiticeid DESC LIMIT 10")
    List<Notice> findNoticeByUserId(String userid);

    @Query("select u from Notice u where u.user_notice.userid=?1 and u.type =?2 and u.subjectid = ?3")
    Notice findNoticeByUserIdandTypeandSubject(String userid, int type, int subject);
    @Modifying
    @Transactional
    @Query("UPDATE Notice u SET u.isseen = :#{#notice.isseen} WHERE u.noiticeid = :#{#notice.noiticeid}")
    void updateisseen(@Param("notice") Notice notice);
    @Modifying
    @Transactional
    @Query("UPDATE Notice u SET u.content = :#{#notice.content} WHERE u.noiticeid = :#{#notice.noiticeid}")
    void updatenotice(@Param("notice") Notice notice);
}
