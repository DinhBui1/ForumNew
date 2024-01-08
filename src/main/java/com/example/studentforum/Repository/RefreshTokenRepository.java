package com.example.studentforum.Repository;
import com.example.studentforum.Model.Post;
import com.example.studentforum.Model.RefreshToken;
import com.example.studentforum.Model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
@EnableJpaRepositories
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken u SET u.token = :#{#token}, u.created=:#{#created} WHERE u.user_refresh.userid = :#{#userId}")
    void updateRefreshToken(@Param("token") String token, @Param("created") Date created, @Param("userId") String userId);
    @Query("select u from RefreshToken u where u.token=?1")
    RefreshToken findRefreshToken(String token);

    @Query("select u from RefreshToken u where u.user_refresh=?1")
    RefreshToken findRefreshTokenbyUserId(User userid);
}
