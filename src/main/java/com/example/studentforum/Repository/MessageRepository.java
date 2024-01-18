package com.example.studentforum.Repository;

import com.example.studentforum.Model.Group_Message;
import com.example.studentforum.Model.Message;
import com.example.studentforum.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    @Query("SELECT u FROM Message u WHERE  (u.messageid = ?1) ")
    Message getMessagesByMessageid(int messageid);

    @Query("SELECT u FROM Message u WHERE  (u.messagename = ?1 ) ")
    Message getMessagesByMessagename(String messagename);


}
