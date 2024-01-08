package com.example.studentforum.Repository;

import com.example.studentforum.Model.Follow;
import com.example.studentforum.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Integer> {
    @Query("SELECT u FROM Follow u WHERE u.user_follow.userid = ?1 and u.user_follower.userid = ?2 ")
    Follow getFollowByUserIdandFollowerId(String userid, String followerid);
    @Query("SELECT u FROM Follow u WHERE u.user_follow.userid = ?1 ")
    List<Follow> getFollowerByUserId(String userid);

    @Query("SELECT u FROM Follow u WHERE u.user_follower.userid = ?1 ")
    List<Follow> getUserIdByFollowerid(String followerid);

    @Modifying
    @Transactional
    @Query("DELETE FROM Follow f WHERE f.followid = ?1")
    void deleteByFollowerId(int followerId);
}
