package com.example.studentforum.Repository;

import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.Post_Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Post_TopicRepository extends JpaRepository<Post_Topic, Integer> {
    @Query("SELECT u FROM Post_Topic u WHERE u.post_posttopic.postid=?1 and u.topic_posttopic.topicid=?2")
    Post_Topic getPost_TopicByPostidandTopicid(int postid, int topicid);

    @Query("SELECT u FROM Post_Topic u WHERE u.posttopicid=?1 ")
    Post_Topic getPost_TopicByPost_topicid(int post_topicid);

    @Query("SELECT u FROM Post_Topic u WHERE u.post_posttopic.postid=?1")
    List<Post_Topic> getPost_TopicByPostid(int postid);

    @Query("SELECT u FROM Post_Topic u WHERE u.topic_posttopic.topicid=?1")
    List<Post_Topic> getPost_TopicByTopicid(int topicid);
}

