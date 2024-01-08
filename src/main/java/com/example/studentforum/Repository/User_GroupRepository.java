package com.example.studentforum.Repository;

import com.example.studentforum.Model.Group;
import com.example.studentforum.Model.User_Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface User_GroupRepository extends JpaRepository<User_Group,Integer> {
    @Query("SELECT u FROM User_Group u WHERE u.user_usergroup.userid=?1 and u.group_usergroup.groupid=?2 ")
    User_Group getUser_GroupByUseridAndGroupId(String userid,int groupid);
    @Query("SELECT u FROM User_Group u WHERE  u.group_usergroup.groupid=?1 ")
    List<User_Group> getUser_GroupByGroupId(int groupid, Pageable pageable);
    @Query("SELECT u FROM User_Group u WHERE  u.group_usergroup.groupid=?1 ")
    List<User_Group> getUser_GroupByGroupIdNopacing(int groupid);
    @Query("SELECT u FROM User_Group u WHERE  u.user_usergroup.userid=?1 and u.checked=1")
    List<User_Group> getUser_GroupByUserId(String userid);

    @Modifying
    @Transactional
    @Query("UPDATE User_Group u SET u.checked = :#{#userGroup.checked} WHERE u.user_groupid = :#{#userGroup.user_groupid}")
    void updateCheck(@Param("userGroup") User_Group userGroup);
}
