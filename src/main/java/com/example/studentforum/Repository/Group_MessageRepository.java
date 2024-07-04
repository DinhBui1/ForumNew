package com.example.studentforum.Repository;

import com.example.studentforum.Model.Group;
import com.example.studentforum.Model.Group_Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Group_MessageRepository extends JpaRepository<Group_Message, Integer> {
    @Query("SELECT u FROM Group_Message u WHERE  u.group_messageid = ?1 ")
    Group_Message getGroup_MessageById(int group_messageid);

    @Query("SELECT u FROM Group_Message u WHERE    LOWER(u.group_messagename) LIKE LOWER(CONCAT('%', :keyword, '%')) or LOWER(u.group_messagedescription) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY u.group_messageid DESC ")
    List<Group_Message> getGroup_MessageByKeyword(String keyword);

    @Modifying
    @Transactional
    @Query("UPDATE Group_Message u SET u.group_messagename = :#{#groupMessage.group_messagename}, u.group_messageimage=:#{#groupMessage.group_messageimage}," +
            " u.group_messagedescription=:#{#groupMessage.group_messagedescription}" +
            "  WHERE u.group_messageid = :#{#groupMessage.group_messageid}")
    void updateGroup_Message(@Param("groupMessage") Group_Message groupMessage);

    @Query("SELECT  gm FROM Group_Message gm " +
            "INNER JOIN Content_GroupMessage c ON gm.group_messageid = c.groupmessage_content.group_messageid " +
            "INNER JOIN DetailGroup_Message d ON gm.group_messageid = d.detailgroupmessage_groupmessage.group_messageid " +
            "WHERE d.user_detailgroupmessage.userid = :userId " +
            "ORDER BY c.createday DESC")
    List<Group_Message> findGroupMessagesByUserIdOrderByLatestMessage(@Param("userId") String userId);

}
