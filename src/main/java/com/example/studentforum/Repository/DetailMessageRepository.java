package com.example.studentforum.Repository;

import com.example.studentforum.Model.Content_Message;
import com.example.studentforum.Model.DetailMessage;
import com.example.studentforum.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailMessageRepository extends JpaRepository<DetailMessage, Integer> {
    @Query("select c from DetailMessage c where c.detailmessage_message.messageid = ?1 and c.user_detailmessage.userid = ?2")
    DetailMessage getDetailMessageByMessageidandUserid(int messageid, String userid);

    @Query("select c from DetailMessage c where c.user_detailmessage.userid = ?1")
    List<DetailMessage> getDetailMessageByUserid(String userid);

    @Query("select c from DetailMessage c where c.detailmessage_message.messageid = ?1 and c.user_detailmessage.userid != ?2")
    DetailMessage getDetailMessageByMessageid(int messageid, String userid);

    @Query("SELECT  gm FROM DetailMessage gm " +
            "INNER JOIN Content_Message c ON gm.detailmessage_message.messageid = c.message_content.messageid " +
            "WHERE gm.user_detailmessage.userid = :userId " +
            "ORDER BY c.createday DESC LIMIT 5")
    List<DetailMessage> findDetailMessagesByUserIdOrderByLatestMessage(@Param("userId") String userId);
}
