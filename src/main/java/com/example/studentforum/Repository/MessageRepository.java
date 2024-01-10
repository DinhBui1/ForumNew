package com.example.studentforum.Repository;

import com.example.studentforum.Model.Message;
import com.example.studentforum.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    @Query("SELECT u FROM Message u WHERE  (u.messageid = ?1 and u.user_message.userid = ?2) ")
    Message getMessagesByMessageid(String messageid, String userid);
}
