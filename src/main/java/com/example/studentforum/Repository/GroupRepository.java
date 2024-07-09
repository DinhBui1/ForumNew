package com.example.studentforum.Repository;

import com.example.studentforum.Model.Group;
import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Query("SELECT u FROM Group u WHERE (u.groupid = ?1) ")
    Group getGroupByGroupId(int groupid);

    @Query("SELECT u FROM Group u WHERE  LOWER(u.groupname) LIKE LOWER(CONCAT('%', :keyword, '%')) ")
    List<Group> getGroupByKeyword(String keyword);

    @Query("SELECT u FROM Group u WHERE  u.user_group.userid=?1 ")
    List<Group> getGroupByAdmin(String adminid);

    @Query("SELECT u FROM Group u ORDER BY u.groupid DESC")
    List<Group> getAllGroup(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Group u SET u.groupname = :#{#group.groupname}, u.image=:#{#group.image}," +
            " u.description=:#{#group.description}" +
            "  WHERE u.groupid = :#{#group.groupid}")
    void updateGroup(@Param("group") Group group);
}
