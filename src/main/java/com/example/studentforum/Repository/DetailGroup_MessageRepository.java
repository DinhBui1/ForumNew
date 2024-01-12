package com.example.studentforum.Repository;

import com.example.studentforum.Model.DetailGroup_Message;
import com.example.studentforum.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface DetailGroup_MessageRepository extends JpaRepository<DetailGroup_Message, Integer> {
    @Query("SELECT u FROM DetailGroup_Message u WHERE  (u.detailgroupmessage_groupmessage.group_messageid = ?1 and u.user_detailgroupmessage.userid=?2) ")
    DetailGroup_Message getDetailGroup_MessageByMessageidandUserid(int messageid, String userid);

    @Query("SELECT u FROM DetailGroup_Message u WHERE  (u.user_detailgroupmessage.userid = ?1) ")
    List<DetailGroup_Message> getDetailGroup_MessageByUserid(String userid);

}
