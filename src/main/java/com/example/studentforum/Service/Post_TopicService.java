package com.example.studentforum.Service;

import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.Post_Topic;
import com.example.studentforum.Model.Topic;
import com.example.studentforum.Repository.PostRepository;
import com.example.studentforum.Repository.Post_TopicRepository;
import com.example.studentforum.Repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Post_TopicService {
    @Autowired
    private Post_TopicRepository postTopicRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TopicRepository topicRepository;

    public String createPost_Topic(int postid, int topicid) {
        Post_Topic postTopic = new Post_Topic();
        Post post = postRepository.getPostById(postid);
        if (post == null) {
            return "Post is not exit";
        }
        Topic topic = topicRepository.getTopicById(topicid);
        if (topic == null) {
            return "Post is not exit";
        }
        postTopic.setPost_posttopic(post);
        postTopic.setTopic_posttopic(topic);
        postTopic.setCreateday(LocalDateTime.now());
        postTopicRepository.save(postTopic);
        return "Create Post_Topic Success";
    }

    public String deletePostTopicbyPostidandTopicid(int postid, int topicid) {
        Post_Topic postTopic = postTopicRepository.getPost_TopicByPostidandTopicid(postid, topicid);
        postTopicRepository.delete(postTopic);
        return "Delete Post_Topic Success";
    }

    public String deletePostTopic(int post_topicid) {
        Post_Topic postTopic = postTopicRepository.getPost_TopicByPost_topicid(post_topicid);
        postTopicRepository.delete(postTopic);
        return "Delete Post_Topic Success";
    }

    public List<Post_Topic> getPost_TopicbyPostid(int postid) {
        return postTopicRepository.getPost_TopicByPostid(postid);
    }
}
