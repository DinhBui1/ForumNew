package com.example.studentforum.Service;

import com.example.studentforum.Model.Group;
import com.example.studentforum.Model.Notice;
import com.example.studentforum.Model.User;
import com.example.studentforum.Model.User_Group;
import com.example.studentforum.Repository.GroupRepository;
import com.example.studentforum.Repository.UserRepository;
import com.example.studentforum.Repository.User_GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class User_GroupService {
    @Autowired
    private User_GroupRepository userGroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private NoticeService noticeService;

    public String joinGroup(String userid, int groupid) {

        User u = userRepository.getUserById(userid);
        Group g = groupRepository.getGroupByGroupId(groupid);
        if (u == null || g == null) {
            return "User or Group Not Exit";
        }
        User_Group ug = userGroupRepository.getUser_GroupByUseridAndGroupId(userid, groupid);
        if (ug != null) {
            return "User has been joined in group";
        }
        if (g.getReputation() > u.getReputation()) {
            return "User isn't enought to reputation";
        }

        User_Group userGroup = new User_Group();
        userGroup.setGroup_usergroup(g);
        userGroup.setUser_usergroup(u);
        userGroup.setCreateday(LocalDateTime.now());
        userGroup.setChecked(0);
        userGroupRepository.save(userGroup);
        String content = "Bạn đã yêu cầu tham gia nhóm: <b>" + g.getGroupname() + "</b>";
        Notice n = noticeService.createNotice(userid, content, 10, 0);
        return "Join Group Success";
    }

    public String leaveGroup(String userid, int groupid) {
        User_Group user_group = userGroupRepository.getUser_GroupByUseridAndGroupId(userid, groupid);
        if (user_group == null) {
            return "User_Group Not Exit";
        }
        userGroupRepository.delete(user_group);
        return "Join Group Success";
    }

    public List<User_Group> getUserinGroup(int groupid, int limit, int pacing) {
        int offset = (pacing - 1);
        Pageable pageable = PageRequest.of(offset, limit);
        return userGroupRepository.getUser_GroupByGroupId(groupid, pageable);
    }

    public List<User_Group> getUserinGroupNopacing(int groupid) {
        return userGroupRepository.getUser_GroupByGroupIdNopacing(groupid);
    }

    public String updateCheck(String userid, int groupid, int check) {

        User_Group user_group = userGroupRepository.getUser_GroupByUseridAndGroupId(userid, groupid);
        if (check == 0) {
            String content = "Admin nhóm: <b>" + user_group.getGroup_usergroup().getGroupname() + "</b> đã từ chối bạn";
            Notice n = noticeService.createNotice(userid, content, 10, 0);
            userGroupRepository.delete(user_group);
            return "Update check Success";
        }
        if (user_group == null) {
            return "User_Group Not Exit";
        }
        user_group.setChecked(check);
        userGroupRepository.updateCheck(user_group);
        String content = "Admin nhóm: <b>" + user_group.getGroup_usergroup().getGroupname() + "</b> đã phê duyệt bạn";
        Notice n = noticeService.createNotice(userid, content, 10, 0);
        return "Update Check Success";
    }


}
