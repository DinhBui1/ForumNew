package com.example.studentforum.Repository;

import com.example.studentforum.Model.Notice;
import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE (u.userid = ?1) ")
    User getUserById(String Id);

    @Query("SELECT u FROM User u ")
    List<User> getUser(Pageable pageable);

    @Query("SELECT u FROM User u WHERE (u.username = ?1 and u.type='UP') ")
    User getUserByUsername(String username);

    @Query("SELECT u FROM User u ORDER BY u.reputation DESC LIMIT 10")
    List<User> getTopReputation();

    @Query("SELECT u FROM User u WHERE YEAR(u.createday)=?1 ")
    List<User> statisticUser(int year);

    @Query("SELECT u FROM User u WHERE u.reputation<=-300")
    List<User> getLowReputation();

    @Query("SELECT u FROM User u WHERE u.isban.isbanid <> 0 ")
    List<User> getListBanUser();

    @Query("SELECT u FROM User u WHERE (u.email = ?1 ) ")
    User getUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE  LOWER(u.fullname) LIKE LOWER(CONCAT('%', :keyword, '%'))  ORDER BY u.userid DESC")
    List<User> getUserByKeyword(String keyword);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = ?2 WHERE u.email = ?1")
    void changePassword(String email, String newpassword);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status = ?2 WHERE u.userid = ?1")
    void setStatusUsser(String userid, int status);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.reputation = :#{#user.reputation} WHERE u.userid = :#{#user.userid}")
    void updateReputation(@Param("user") User user);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.fullname = :#{#user.fullname}, u.birthday=:#{#user.birthday}," +
            " u.gender=:#{#user.gender}, u.image=:#{#user.image}, u.phone=:#{#user.phone}," +
            " u.address=:#{#user.address} WHERE u.userid = :#{#user.userid}")
    void updateaccount(@Param("user") User user);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isban = :#{#user.isban} WHERE u.userid = :#{#user.userid}")
    void banUser(@Param("user") User user);
}
