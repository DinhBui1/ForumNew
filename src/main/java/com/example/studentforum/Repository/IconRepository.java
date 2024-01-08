package com.example.studentforum.Repository;

import com.example.studentforum.Model.Icon;
import com.example.studentforum.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IconRepository extends JpaRepository<Icon,Integer> {
    @Query("SELECT u FROM Icon u WHERE (u.iconid = ?1) ")
    Icon getIconById(int Id);
}
