package com.example.studentforum.Repository;

import com.example.studentforum.Model.Content_Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Content_MessageRepositpry extends JpaRepository<Content_Message, Integer> {
    @Query("select c from Content_Message c where c.contentid = ?1")
    Content_Message getContent_MessageByContentid(String contentid);

    @Query("select c from Content_Message c where c.message_content.messageid = ?1")
    List<Content_Message> getContent_MessageByMessageid(String messageid);
}
