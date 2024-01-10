package com.example.studentforum.Repository;

import com.example.studentforum.Model.DetailGroup_Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailGroup_MessageRepository extends JpaRepository<DetailGroup_Message, Integer> {
}
