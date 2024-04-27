package com.example.studentforum.Control;

import com.example.studentforum.Model.Group;
import com.example.studentforum.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GroupController {
    @Autowired
    private GroupService groupService;

    @MutationMapping
    public String create_group(@Argument Group group, @Argument String admin) {
        return groupService.createGroup(group, admin);
    }

    @MutationMapping
    public String update_group(@Argument Group group) {
        return groupService.updateGroup(group);
    }

    @MutationMapping
    public String delete_group(@Argument int groupid) {
        return groupService.deleteGroup(groupid);
    }

    @QueryMapping
    public List<Group> find_group_by_keyword(@Argument String keyword, @Argument String userid) {
        return groupService.findGroupbyKeyword(keyword, userid);
    }

    @QueryMapping
    public List<Group> get_group_by_admin(@Argument String admin) {
        return groupService.getGroupbyAdmin(admin);
    }

    @QueryMapping
    public List<Group> get_group_by_userid(@Argument String userid) {
        return groupService.getGroupbyUserid(userid);
    }

    @QueryMapping
    public Group get_group_by_groupid(@Argument int groupid) {
        return groupService.getGroupbyGroupid(groupid);
    }

}
