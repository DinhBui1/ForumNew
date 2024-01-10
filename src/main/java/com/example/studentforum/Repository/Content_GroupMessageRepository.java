package com.example.studentforum.Repository;

import com.example.studentforum.Model.Content_GroupMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Content_GroupMessageRepository extends JpaRepository<Content_GroupMessage, Integer> {
}
