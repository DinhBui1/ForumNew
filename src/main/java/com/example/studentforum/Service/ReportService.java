package com.example.studentforum.Service;

import com.example.studentforum.Model.*;
import com.example.studentforum.Repository.CommentRepository;
import com.example.studentforum.Repository.PostRepository;
import com.example.studentforum.Repository.ReportRepository;
import com.example.studentforum.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private NoticeService noticeService;

    public String createReportUser(Report report, String userid,String user_rpid){
        if(checkcreateReport(1,user_rpid)==1){
            return "Your turn to report has expired";
        }
        User reporter = userRepository.getUserById(user_rpid);
        report.setUser_reporter(reporter);

        User u = userRepository.getUserById(userid);
        report.setUser_report(u);

        report.setCreateday(LocalDateTime.now());
        reportRepository.save(report);
        String content = "Hệ thống đã ghi nhận báo cáo của bạn";
        Notice n = noticeService.createNotice(reporter.getUserid(),content,9,0);
        return "Create Report User Success";
    }
    public String createReportPost(Report report, int postid,String user_rpid){
        if(checkcreateReport(2,user_rpid)==1){
            return "Your turn to report has expired";
        }
        User reporter = userRepository.getUserById(user_rpid);
        report.setUser_reporter(reporter);

        Post p = postRepository.getPostById(postid);
        report.setPost_report(p);

        report.setCreateday(LocalDateTime.now());
        reportRepository.save(report);
        String content = "Hệ thống đã ghi nhận báo cáo của bạn";
        Notice n = noticeService.createNotice(reporter.getUserid(),content,10,p.getPostid());
        return "Create Report Post Success";
    }
    public String createReportComment(Report report, int commentid,String user_rpid){
        if(checkcreateReport(3,user_rpid)==1){
            return "Your turn to report has expired";
        }
        User reporter = userRepository.getUserById(user_rpid);
        report.setUser_reporter(reporter);

        Comment c = commentRepository.getCommentById(commentid);
        report.setComment_report(c);

        report.setCreateday(LocalDateTime.now());
        reportRepository.save(report);
        String content = "Hệ thống đã ghi nhận báo cáo của bạn";
        Notice n = noticeService.createNotice(reporter.getUserid(),content,11,c.getCommentid());
        return "Create Report Comment Success";
    }

    public List<Report> getallListReportByType(int type){
        return reportRepository.getallListReportbyType(type);
    }

    public String deleteReportByUserId(String userid){
        List<Report> reports =reportRepository.getReportbyUserid(userid);
        for (Report r : reports){
            reportRepository.delete(r);
        }
        return "Delete Report By UserId Success";
    }

    public String deleteReportByPostId(int postid){
        List<Report> reports =reportRepository.getReportbyPostid(postid);
        for (Report r : reports){
            reportRepository.delete(r);
        }
        return "Delete Report By PostId Success";
    }
    public String deleteReportByCommentId(int commentid){
        List<Report> reports =reportRepository.getReportbyCommentid(commentid);
        for (Report r : reports){
            reportRepository.delete(r);
        }
        return "Delete Report By CommentId Success";
    }
    public int checkcreateReport(int type, String userid){
        int checked =0;
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        List<Report> reports = reportRepository.getallListReportbyTypeandUserid(type,userid);

        for(Report report :reports){

            if(report.getCreateday().isAfter(yesterday)){

                return 1;
            }

        }
        return checked;
    }
}
