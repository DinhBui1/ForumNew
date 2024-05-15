package com.example.studentforum.Control;

import com.example.studentforum.DTO.PostDTO;
import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.Topic;
import com.example.studentforum.Model.User;
import com.example.studentforum.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;

    @QueryMapping
    public List<PostDTO> post(@Argument int limit, @Argument int pacing) {
        return postService.getallPost(limit, pacing);
    }

    @MutationMapping
    public String update_post_by_pk(@Argument("post") Post post, @Argument("topic") List<Topic> topic) {
        return postService.updatePostById(post, topic);
    }

    @MutationMapping
    public String delete_post_by_pk(@Argument int postid) {
        return postService.deletePostById(postid);
    }

    @MutationMapping
    public String create_post(@Argument("post") Post post, @Argument("user") User user, @Argument("topic") List<Topic> topic) {
        return postService.createPost(post, user, topic);
    }

    @QueryMapping
    public PostDTO find_post_by_id(@Argument int postid) {
        return postService.findPostByid(postid);
    }

    @MutationMapping
    public String hide_post(@Argument int postid) {
        return postService.hidePostById(postid);
    }

    @MutationMapping
    public String update_totalread_post(@Argument int postid, @Argument String userid) {
        return postService.updateTotalReadPost(postid, userid);
    }

    @QueryMapping
    public List<PostDTO> find_post_by_userid(@Argument String userid) {
        return postService.findPostByUserid(userid);
    }

    @QueryMapping
    public List<PostDTO> find_post_by_topicid(@Argument int topicid) {
        return postService.findPostbyTopicid(topicid);
    }

    @QueryMapping
    public List<PostDTO> find_post_by_keyword(@Argument String keyword, @Argument String userid) {
        return postService.findPostByKeyword(keyword, userid);
    }

    @MutationMapping
    public String create_post_in_group(@Argument("post") Post post, @Argument("user") User user, @Argument("topic") List<Topic> topic, @Argument int groupid) {
        return postService.createPostinGroup(post, user, topic, groupid);
    }

    @QueryMapping
    public List<PostDTO> find_post_in_group(@Argument int groupid) {
        return postService.getPostinGroup(groupid);
    }

    @QueryMapping
    public int[] statistic_post(@Argument int year) {
        return postService.statisticPost(year);
    }

    @QueryMapping
    public int[] statistic_post_in_topic() {
        return postService.staticPostinTopic();
    }

    @QueryMapping
    public List<PostDTO> find_post_by_follow(@Argument String userid) {
        return postService.findPostByFollowid(userid);
    }

    @QueryMapping
    public List<PostDTO> find_post_by_listtopicid(@Argument("topicids") List<Integer> topicids) {
        return postService.findPostByListTopicId(topicids);
    }
}
