package com.example.studentforum.Service;

import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Post_LikeRepository post_likeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private FollowService followService;
    @Autowired
    private ViewPostService viewPostService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private Post_TopicRepository postTopicRepository;
    @Autowired
    private Post_TopicService postTopicService;

    public List<Post> getallPost(int limit, int pacing) {

        int offset = (pacing - 1);
        Pageable pageable = PageRequest.of(offset, limit);
        return postRepository.getPost(pageable);
    }

    public String updatePostById(Post post, List<Topic> topic) {
        Post p = postRepository.getPostById(post.getPostid());
        if (p.getUser_post().getIsban().getIsbanid() != 0) {
            return "User has been exit ban list";
        }
        if (p == null) {
            return "Post Not Exit";
        }
        List<Post_Topic> postTopics = postTopicRepository.getPost_TopicByPostid(p.getPostid());
        for (Post_Topic postTopic : postTopics) {
            postTopicService.deletePostTopic(postTopic.getPosttopicid());
        }
        if (topic != null) {
            for (Topic topic1 : topic) {
                postTopicService.createPost_Topic(p.getPostid(), topic1.getTopicid());
            }
        }
        p.setTitle(post.getTitle());
        p.setContent(post.getContent());
        p.setRequiredreputation(post.getRequiredreputation());
        p.setUpdateday(new Date());
        postRepository.updatepost(p);
        return "Update Post Success";
    }

    public String deletePostById(int id) {
        try {
            Post p = postRepository.getPostById(id);
            List<Post_Topic> postTopics = postTopicRepository.getPost_TopicByPostid(p.getPostid());
            for (Post_Topic postTopic : postTopics) {
                postTopicService.deletePostTopic(postTopic.getPosttopicid());
            }
            List<Post_Like> pls = post_likeRepository.getPostLikeByPostId(id);
            if (pls != null) {
                for (Post_Like pl : pls) {
                    post_likeRepository.delete(pl);
                }
            }
            viewPostService.deleteViewPostbyPostid(p.getPostid());
            postRepository.delete(p);
            userService.updateReputation(p.getUser_post().getUserid(), -20);
            return "Delete Success";
        } catch (Exception e) {
            return "Delete Failure";
        }
    }

    public String createPost(Post post, User user, List<Topic> topic) {
        User u = userRepository.getUserById(user.getUserid());
        if (u.getIsban().getIsbanid() != 0) {
            return "User has been exit ban list";
        }
        post.setUser_post(u);
        post.setIshide(0);
        post.setCreateday(LocalDateTime.now());
        post.setTotalread(0);
        Post savedPost = postRepository.save(post);

        if (topic != null) {
            for (Topic topic1 : topic) {
                postTopicService.createPost_Topic(savedPost.getPostid(), topic1.getTopicid());
            }
        }

        List<User> users = followService.getallFollowerByUserId(u.getUserid());
        for (User us : users) {
            String content = "<b>" + u.getFullname() + "</b> vừa đăng một bài viết";
            Notice n = noticeService.createNotice(us.getUserid(), content, 6, 0);
        }
        userService.updateReputation(u.getUserid(), 20);
        return "Create Post Success";
    }

    public String hidePostById(int id) {
        Post p = postRepository.getPostById(id);
        p.setIshide(1);
        postRepository.hidepost(p);
        return "Hide Post Success";
    }

    public String updateTotalReadPost(int postid, String userid) {
        int addview = viewPostService.createViewPost(userid, postid);
        Post p = postRepository.getPostById(postid);
        p.setTotalread(p.getTotalread() + addview);
        postRepository.updateviewpost(p);
        return "Update TotalRead Success";
    }

    public Post findPostByid(int id) {
        return postRepository.getPostById(id);
    }

    public List<Post> findPostbyTopicid(int topicid) {
        List<Post_Topic> postTopics = postTopicRepository.getPost_TopicByTopicid(topicid);
        List<Post> postList = new ArrayList<>();
        for (Post_Topic postTopic : postTopics) {
            Post post = postRepository.getPostById(postTopic.getPost_posttopic().getPostid());
            postList.add(post);
        }
        return postList;
    }

    public List<Post> findPostByKeyword(String keyword) {
        return postRepository.getPostByKeyword(keyword);
    }

    public List<Post> findPostByUserid(String userid) {
        return postRepository.getPostByUserid(userid);
    }

    public String createPostinGroup(Post post, User user, List<Topic> topic, int groupid) {
        User u = userRepository.getUserById(user.getUserid());
        Group g = groupRepository.getGroupByGroupId(groupid);
        if (u.getIsban().getIsbanid() != 0) {
            return "User has been exit ban list";
        }
        post.setUser_post(u);
        post.setIshide(0);
        post.setCreateday(LocalDateTime.now());
        post.setGroup_post(g);
        post.setTotalread(0);
        Post savedPost = postRepository.save(post);

        if (topic != null) {
            for (Topic topic1 : topic) {
                postTopicService.createPost_Topic(savedPost.getPostid(), topic1.getTopicid());
            }
        }

        userService.updateReputation(u.getUserid(), 20);
        return "Create Post Success";
    }

    public List<Post> getPostinGroup(int groupid) {
        return postRepository.getPostinGroup(groupid);
    }

    public int[] statisticPost(int year) {
        List<Post> posts = postRepository.statisticPost(year);
        int[] statistic = new int[12];
        for (Post post : posts) {
            int month = post.getCreateday().getMonthValue();
            statistic[month - 1]++;
        }
        return statistic;
    }

    public int[] staticPostinTopic() {
        List<Post_Topic> postTopics = postTopicRepository.findAll();
        List<Topic> topics = topicRepository.findAll();
        Map<Integer, Integer> postCountByTopicId = new LinkedHashMap<>();


        for (Topic topic : topics) {
            postCountByTopicId.put(topic.getTopicid(), 0);
        }

        for (Post_Topic postTopic : postTopics) {
            int topicId = postTopic.getTopic_posttopic().getTopicid();
            if (postCountByTopicId.containsKey(topicId)) {
                int currentCount = postCountByTopicId.get(topicId);
                postCountByTopicId.put(topicId, currentCount + 1);
            }
        }
        int[] statistic = postCountByTopicId.values().stream().mapToInt(Integer::intValue).toArray();

        return statistic;
    }
}
