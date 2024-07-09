package com.example.studentforum.Service;

import com.example.studentforum.Config.RedisManager;
import com.example.studentforum.DTO.GroupDTO;
import com.example.studentforum.Model.Group;
import com.example.studentforum.Model.Notice;
import com.example.studentforum.Model.User;
import com.example.studentforum.Model.User_Group;
import com.example.studentforum.Repository.GroupRepository;
import com.example.studentforum.Repository.UserRepository;
import com.example.studentforum.Repository.User_GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    @Qualifier("redisTemplates")
    private RedisTemplate<String, String> template;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private User_GroupService userGroupService;
    @Autowired
    private User_GroupRepository userGroupRepository;
    @Autowired
    private NoticeService noticeService;

    public GroupDTO createGroup(Group g, String admin) {
        User u = userRepository.getUserById(admin);
        g.setUser_group(u);
        g.setCreateday(LocalDateTime.now());
        groupRepository.save(g);
        GroupDTO groupDTO = groupToGroupDTO(g);
        String content = "Bạn đã tạo thành công nhóm : <b>" + g.getGroupname() + "</b>";
        Notice n = noticeService.createNotice(u.getUserid(), content, 10, 0);
        return groupDTO;
    }

    public String updateGroup(Group g) {
        Group group = groupRepository.getGroupByGroupId(g.getGroupid());
        if (group == null) {
            return "Group Not Exit";
        }
        group.setGroupname(g.getGroupname());
        group.setImage(g.getImage());
        group.setReputation(g.getReputation());
        group.setDescription(g.getDescription());
        groupRepository.updateGroup(group);
        return "Update Group Success";
    }

    public String deleteGroup(int groupid) {
        Group g = groupRepository.getGroupByGroupId(groupid);
        if (g == null) {
            return "Group Not Exit";
        }
        List<User_Group> userGroups = userGroupService.getUserinGroupNopacing(groupid);
        for (User_Group userGroup : userGroups) {
            String content = "Nhóm : <b>" + g.getGroupname() + "</b> đã bị xoá";
            Notice n = noticeService.createNotice(userGroup.getUser_usergroup().getUserid(), content, 10, 0);
            userGroupService.leaveGroup(userGroup.getUser_usergroup().getUserid(), groupid);
        }
        groupRepository.delete(g);
        return "Delete Group Success";
    }

    public List<Group> getAllGroup(int limit, int pacing) {
        int offset = (pacing - 1);
        Pageable pageable = PageRequest.of(offset, limit);
        return groupRepository.getAllGroup(pageable);
    }

    public List<Group> findGroupbyKeyword(String keyword, String userid) {
        List<String> listValues = template.opsForList().range(userid, 0, -1);
        if (keyword != null) {
            if (!listValues.contains(keyword)) {
                template.opsForList().leftPush(userid, keyword);
            } else {
                template.opsForList().remove(userid, 0, keyword);
                template.opsForList().leftPush(userid, keyword);
            }
        }
        return groupRepository.getGroupByKeyword(keyword);
    }

    public List<Group> getGroupbyAdmin(String adminid) {
        return groupRepository.getGroupByAdmin(adminid);
    }

    public List<Group> getGroupbyUserid(String userid) {
        List<User_Group> userGroups = userGroupRepository.getUser_GroupByUserId(userid);
        List<Group> groups = new ArrayList<>();
        for (User_Group userGroup : userGroups) {
            Group g = groupRepository.getGroupByGroupId(userGroup.getGroup_usergroup().getGroupid());
            groups.add(g);
        }
        return groups;
    }

    public Group getGroupbyGroupid(int groupid) {
        Group g = groupRepository.getGroupByGroupId(groupid);
        return groupRepository.getGroupByGroupId(groupid);
    }

    private GroupDTO groupToGroupDTO(Group g) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupid(g.getGroupid());
        groupDTO.setGroupname(g.getGroupname());
        groupDTO.setImage(g.getImage());
        groupDTO.setCreateday(g.getCreateday());
        groupDTO.setReputation(g.getReputation());
        groupDTO.setAdmin(g.getUser_group().getUserid());
        groupDTO.setDescription(g.getDescription());
        groupDTO.setChanel(g.getChanel());
        groupDTO.setTotaluser(1 + userGroupService.getUserinGroupNopacing(g.getGroupid()).size());
        return groupDTO;
    }


}
