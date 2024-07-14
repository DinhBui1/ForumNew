package com.example.studentforum.Control;

import com.example.studentforum.Model.Report;
import com.example.studentforum.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReportController {
    @Autowired
    private ReportService reportService;

    @MutationMapping
    public String create_report_user(@Argument("report") Report report, @Argument("userid") String userid, @Argument("reporterid") String reporterid) {
        return reportService.createReportUser(report, userid, reporterid);
    }

    @MutationMapping
    public String create_report_post(@Argument("report") Report report, @Argument("postid") int postid, @Argument("reporterid") String reporterid) {
        return reportService.createReportPost(report, postid, reporterid);
    }

    @MutationMapping
    public String create_report_comment(@Argument("report") Report report, @Argument("commentid") int commentid, @Argument("reporterid") String reporterid) {
        return reportService.createReportComment(report, commentid, reporterid);
    }

    @QueryMapping
    public List<Report> get_report_by_type(@Argument("type") int type) {
        return reportService.getallListReportByType(type);
    }

    @QueryMapping
    public Report get_report_by_id(@Argument int id) {
        return reportService.getReportByid(id);
    }

    @MutationMapping
    public String delete_report_by_userid(@Argument("userid") String userid) {
        return reportService.deleteReportByUserId(userid);
    }

    @MutationMapping
    public String delete_report_by_postid(@Argument("postid") int postid) {
        return reportService.deleteReportByPostId(postid);
    }

    @MutationMapping
    public String delete_report_by_commentid(@Argument("commentid") int commentid) {
        return reportService.deleteReportByCommentId(commentid);
    }
}
