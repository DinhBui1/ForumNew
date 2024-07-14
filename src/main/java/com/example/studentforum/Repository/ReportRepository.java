package com.example.studentforum.Repository;

import com.example.studentforum.Model.Report;
import com.example.studentforum.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {
    @Query("SELECT u FROM Report u WHERE (u.type = ?1 ) ")
    List<Report> getallListReportbyType(int type);

    @Query("SELECT u FROM Report u WHERE (u.type = ?1 and u.user_reporter.userid=?2) ")
    List<Report> getallListReportbyTypeandUserid(int type, String userid);

    @Query("SELECT u FROM Report u WHERE (u.user_report.userid = ?1 ) ")
    List<Report> getReportbyUserid(String userid);

    @Query("SELECT u FROM Report u WHERE (u.post_report.postid = ?1 ) ")
    List<Report> getReportbyPostid(int postid);

    @Query("SELECT u FROM Report u WHERE (u.comment_report.commentid = ?1 ) ")
    List<Report> getReportbyCommentid(int commentid);

    @Query("SELECT u FROM Report u WHERE (u.reportid = ?1 ) ")
    Report getReportById(int id);

}
