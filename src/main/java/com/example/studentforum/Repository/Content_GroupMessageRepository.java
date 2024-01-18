package com.example.studentforum.Repository;

import com.example.studentforum.Model.Content_GroupMessage;
import com.example.studentforum.Model.Group_Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Content_GroupMessageRepository extends JpaRepository<Content_GroupMessage, Integer> {

    @Query("SELECT u FROM Content_GroupMessage u WHERE (u.content_groupmessageid = ?1) ")
    public Content_GroupMessage findByContentId(int contentId);

    @Query("SELECT u FROM Content_GroupMessage u WHERE u.groupmessage_content.group_messageid = ?1 ")
    public List<Content_GroupMessage> getContent_GroupMessagebyGroupmessageidandUserid(int groupmessageId);

    @Query("SELECT u FROM Content_GroupMessage u WHERE (u.contentGroupMessageResponse.content_groupmessageid = ?1) ")
    public List<Content_GroupMessage> findByContentResponseId(int contentresponseId);

    @Query("SELECT u FROM Content_GroupMessage u WHERE u.groupmessage_content.group_messageid = ?1 ")
    public List<Content_GroupMessage> findBygroupmessage(int groupmessageis);

    @Query("SELECT cgm FROM Content_GroupMessage cgm " +
            "WHERE cgm.groupmessage_content.group_messageid = :groupmessageId " +
            "ORDER BY cgm.createday DESC LIMIT 50")
    List<Content_GroupMessage> findGroupMessagesByUserIdOrderByLatestMessage(@Param("groupmessageId") int groupmessageid);
}
