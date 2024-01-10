package com.example.studentforum.Repository;

import com.example.studentforum.Model.Group_Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Group_MessageRepository extends JpaRepository<Group_Message, Integer> {
}
