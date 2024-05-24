package com.example.studentforum.Repository;

import com.example.studentforum.Model.ContentGroupMessage_Icon;
import com.example.studentforum.Model.ContentMessager_Icon;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentMessage_IconRepository extends JpaRepository<ContentMessager_Icon, Integer> {

    @Query("SELECT u FROM ContentMessager_Icon u WHERE u.content_iconcontentmessage.contentid = ?1")
    List<ContentMessager_Icon> getContentMessage_IconByContentMessageid(int contentmessageid);

    @Query("SELECT u FROM ContentMessager_Icon u WHERE u.content_iconcontentmessage.contentid = ?1 and u.user_iconcontentmessage.userid = ?2")
    ContentMessager_Icon getContentMessage_IconByContentMessageidandIconidandUserid(int contentmessageid, String userid);
}
