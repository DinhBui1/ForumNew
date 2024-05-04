package com.example.studentforum.Repository;

import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Query("SELECT u FROM Topic u WHERE (u.topicid = ?1) ")
    Topic getTopicById(int Id);

    @Query("SELECT u FROM Topic u WHERE LOWER(u.topicname) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Topic> getTopicByName(String name);
}
