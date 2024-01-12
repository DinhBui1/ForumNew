package com.example.studentforum.Repository;

import com.example.studentforum.Model.Group;
import com.example.studentforum.Model.Group_Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface Group_MessageRepository extends JpaRepository<Group_Message, Integer> {
    @Query("SELECT u FROM Group_Message u WHERE  u.group_messageid = ?1 ")
    Group_Message getGroup_MessageById(int group_messageid);

    @Modifying
    @Transactional
    @Query("UPDATE Group_Message u SET u.group_messagename = :#{#groupMessage.group_messagename}, u.group_messageimage=:#{#groupMessage.group_messageimage}," +
            " u.group_messagedescription=:#{#groupMessage.group_messagedescription}" +
            "  WHERE u.group_messageid = :#{#groupMessage.group_messageid}")
    void updateGroup_Message(@Param("groupMessage") Group_Message groupMessage);
}
