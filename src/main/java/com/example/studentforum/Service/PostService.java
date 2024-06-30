package com.example.studentforum.Service;

import com.example.studentforum.Config.RedisManager;
import com.example.studentforum.DTO.PostDTO;
import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.*;
import org.apache.tomcat.util.descriptor.tld.TldRuleSet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

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
    @Autowired
    private Post_LikeService postLikeService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private GenminiService genminiService;

    public List<PostDTO> getallPost(int limit, int pacing) {

        int offset = (pacing - 1);
        Pageable pageable = PageRequest.of(offset, limit);
        List<Post> posts = postRepository.getPost(pageable);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = this.setPostToPostDTO(post);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }

    public String updatePostById(Post post, List<Topic> topic) {
        Post p = postRepository.getPostById(post.getPostid());
        if (p.getUser_post().getIsban().getIsbanid() != 0) {
            return "User has been exit ban list";
        }
        if (p == null) {
            return "Post Not Exit";
        }
        String checkPostTitle = genminiService.callApi(post.getTitle());
        if (checkPostTitle.contains("yes")) {
            String parts = checkPostTitle.split("\\[")[1].split("]")[0];
            p.setWarning("1");
            p.setWarningword(parts);
        } else {
            String checkPostContent = genminiService.callApi(post.getContent());
            if (checkPostContent.contains("yes")) {
                String parts = checkPostContent.split("\\[")[1].split("]")[0];
                p.setWarning("1");
                p.setWarningword(parts);
            } else {
                p.setWarning("0");
                p.setWarningword(null);
            }
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
        String checkPostTitle = genminiService.callApi(post.getTitle());
        if (checkPostTitle.contains("yes")) {
            String parts = checkPostTitle.split("\\[")[1].split("]")[0];
            post.setWarning("1");
            post.setWarningword(parts);
        } else {
            String checkPostContent = genminiService.callApi(post.getContent());
            if (checkPostContent.contains("yes")) {
                String parts = checkPostContent.split("\\[")[1].split("]")[0];
                post.setWarning("1");
                post.setWarningword(parts);
            } else {
                post.setWarning("0");
                post.setWarningword(null);
            }
        }
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

    public PostDTO findPostByid(int id) {
        Post p = postRepository.getPostById(id);
        PostDTO postDTO = this.setPostToPostDTO(p);
        return postDTO;
    }

    public List<PostDTO> findPostbyTopicid(int topicid) {
        List<Post_Topic> postTopics = postTopicRepository.getPost_TopicByTopicid(topicid);
        List<PostDTO> postList = new ArrayList<>();
        for (Post_Topic postTopic : postTopics) {
            Post post = postRepository.getPostById(postTopic.getPost_posttopic().getPostid());
            PostDTO postDTO = this.setPostToPostDTO(post);
            postList.add(postDTO);
        }
        return postList;
    }

    public List<PostDTO> findPostByKeyword(String keyword, String userid) {
        Jedis jedis = RedisManager.getConnection();
        jedis.lpush(userid, keyword);
        List<Post> posts = postRepository.getPostByKeyword(keyword);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = this.setPostToPostDTO(post);
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    public List<PostDTO> findPostByUserid(String userid) {
        List<Post> posts = postRepository.getPostByUserid(userid);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = this.setPostToPostDTO(post);
            postDTOs.add(postDTO);
        }
        return postDTOs;
    }

    public String createPostinGroup(Post post, User user, List<Topic> topic, int groupid) {
        User u = userRepository.getUserById(user.getUserid());
        Group g = groupRepository.getGroupByGroupId(groupid);
        if (u.getIsban().getIsbanid() != 0) {
            return "User has been exit ban list";
        }
        String checkPostTitle = genminiService.callApi(post.getTitle());
        if (checkPostTitle.contains("yes")) {
            String parts = checkPostTitle.split("\\[")[1].split("]")[0];
            post.setWarning("1");
            post.setWarningword(parts);
        } else {
            String checkPostContent = genminiService.callApi(post.getContent());
            if (checkPostContent.contains("yes")) {
                String parts = checkPostContent.split("\\[")[1].split("]")[0];
                post.setWarning("1");
                post.setWarningword(parts);
            } else {
                post.setWarning("0");
                post.setWarningword(null);
            }
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

    public List<PostDTO> getPostinGroup(int groupid) {
        List<Post> posts = postRepository.getPostinGroup(groupid);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (Post post : posts) {
            PostDTO postDTO = this.setPostToPostDTO(post);
            postDTOs.add(postDTO);
        }
        return postDTOs;
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

    public List<PostDTO> findPostByFollowid(String userid) {
        List<User> follows = followService.getallUserIdByFollower(userid);
        List<PostDTO> postDTOs = new ArrayList<>();
        for (User user : follows) {
            List<Post> posts = postRepository.getPostByUserid(user.getUserid());
            for (Post post : posts) {
                PostDTO postDTO = this.setPostToPostDTO(post);
                postDTOs.add(postDTO);
            }
        }
        return postDTOs;
    }

    private PostDTO setPostToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        postDTO.setTotallike(postLikeService.CountTotalLikeByPost(post.getPostid()));
        postDTO.setTotaldislike(postLikeService.CountTotalDisLikeByPost(post.getPostid()));
        postDTO.setListtopic(postTopicService.getPost_TopicbyPostid(post.getPostid()));
        postDTO.setTotalcomment(commentService.totalCommentbyPostid(post.getPostid()));
        return postDTO;
    }

    public List<PostDTO> findPostByListTopicId(List<Integer> topics) {
        List<PostDTO> posts = new ArrayList<>();
        for (Integer topic : topics) {
            List<PostDTO> postdtos = this.findPostbyTopicid(topic);
            if (posts.isEmpty()) {
                posts.addAll(postdtos);
            } else {
                List<PostDTO> commonPosts = new ArrayList<>();
                for (PostDTO p : posts) {
                    for (PostDTO pdto : postdtos) {
                        if (p.getPostid() == pdto.getPostid()) {
                            commonPosts.add(pdto);
                        }
                    }
                }
                posts.clear();
                posts.addAll(commonPosts);
            }
        }
        return posts;
    }
}
