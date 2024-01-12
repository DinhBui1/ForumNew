package com.example.studentforum.Repository;

import com.example.studentforum.Model.ContentGroupMessage_Icon;
import com.example.studentforum.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContentGroupMessage_IconRepository extends JpaRepository<ContentGroupMessage_Icon, Integer> {
    @Query("SELECT u FROM ContentGroupMessage_Icon u WHERE u.user_iconcontentgroupmessage.userid = ?1 and u.content_iconcontentgroupmessage.content_groupmessageid= ?2")
    ContentGroupMessage_Icon getContentGroupMessage_IconByUseridandMessageid(String userid, int messageid);

    @Query("SELECT u FROM ContentGroupMessage_Icon u WHERE u.content_iconcontentgroupmessage.content_groupmessageid= ?1")
    List<ContentGroupMessage_Icon> getContentGroupMessage_IconByMessageid(int messageid);

}
