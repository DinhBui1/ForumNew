package com.example.studentforum.Repository;

import com.example.studentforum.Model.IsBan;
import com.example.studentforum.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IsBanRepository extends JpaRepository<IsBan, Integer> {
    @Query("SELECT u FROM IsBan u WHERE (u.isbanid = ?1 ) ")
    IsBan getIsBanByIsbanid(int isbanid);
}
