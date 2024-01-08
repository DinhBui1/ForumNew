package com.example.studentforum.Control;

import com.example.studentforum.Authetication.CheckRole;
import com.example.studentforum.Authetication.RoleAdmin;
import com.example.studentforum.Authetication.RoleAll;
import com.example.studentforum.Model.User;
import com.example.studentforum.Service.UserService;
import com.example.studentforum.exception.UnauthorizedException;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public List<User> account(@Argument int limit, @Argument int pacing) {
//        List<GrantedAuthority> authorities =CheckRole.getRoleClient();
//        List<GrantedAuthority> role = RoleAll.getRoleAll();
//        if(CheckRole.checkRole(authorities,role)==1){
        return userService.getallUser(limit, pacing);
//        }
//        throw new UnauthorizedException("UNAUTHORIZED");
    }

    @MutationMapping
    public User account_update(@Argument("user") User user) {
        return userService.account_update(user);

    }

    @MutationMapping
    public String logout(@Argument String userid) {
        return userService.logout(userid);
    }

    @SubscriptionMapping
    public Publisher<User> sub_status_user(@Argument String userid) {
        return userService.subStatusUserByUserid(userid);
    }

    @QueryMapping
    public User find_account_by_id(@Argument String userid) {
        return userService.findUserById(userid);
    }

    @QueryMapping
    public List<User> get_top_reputation_user() {
        return userService.getTopReputationUser();
    }

    @MutationMapping
    public User ban_user(@Argument("userid") String userid, @Argument("isbanid") int isbanid) {
        return userService.checkBanUser(userid, isbanid);
    }

    @QueryMapping
    public List<User> get_list_low_reputation() {
        return userService.getListLowReputationUser();
    }

    @QueryMapping
    public List<User> get_list_ban_user() {
        return userService.getListBanUser();
    }

    @QueryMapping
    public int[] statistic_user(@Argument int year) {
        return userService.statisticUser(year);
    }

    @MutationMapping
    public String update_reputation(@Argument String userid, @Argument int reputation) {
        userService.updateReputation(userid, reputation);
        return "Update reputation success";
    }

    @QueryMapping
    public List<User> get_user_by_keyword(@Argument String keyword) {
        return userService.getUserbayKeyword(keyword);
    }
}
