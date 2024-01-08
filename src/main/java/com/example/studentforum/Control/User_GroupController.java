package com.example.studentforum.Control;

import com.example.studentforum.Model.User_Group;
import com.example.studentforum.Service.User_GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class User_GroupController {
    @Autowired
    private User_GroupService userGroupService;

    @MutationMapping
    public String join_group(@Argument String userid, @Argument int groupid){
        return userGroupService.joinGroup(userid,groupid);
    }

    @MutationMapping
    public String leave_group(@Argument String userid, @Argument int groupid){
        return userGroupService.leaveGroup(userid,groupid);
    }

    @QueryMapping
    public List<User_Group> get_user_in_group( @Argument int groupid,@Argument int limit, @Argument int pacing){
        return userGroupService.getUserinGroup(groupid,limit, pacing);
    }
    @MutationMapping
    public String update_check(@Argument String userid, @Argument int groupid, @Argument int check){
        return userGroupService.updateCheck(userid,groupid,check);
    }
}
