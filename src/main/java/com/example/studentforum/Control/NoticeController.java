package com.example.studentforum.Control;

import com.example.studentforum.Model.Notice;
import com.example.studentforum.Service.NoticeService;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.Flow;

@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @MutationMapping
    public Notice create_notice(@Argument("userid") String userid,@Argument("content") String content,@Argument("type") int type,@Argument("subject") int subject){
        return noticeService.createNotice(userid,content,type,subject);
    }
    @MutationMapping
    public String delete_notice(@Argument("noticeid") int noticeid){
        return noticeService.deleteNotice(noticeid);
    }
    @QueryMapping
    public List<Notice> find_all_notice_by_userid(@Argument("userid") String userid){
        return noticeService.getallNoticeByUserid(userid);
    }
    @MutationMapping
    public Notice update_isseen_true(@Argument("noticeid") int noticeid){
        return noticeService.updateIsseenTrue(noticeid);
    }
    @MutationMapping
    public Notice update_isseen_false(@Argument("noticeid") int noticeid){
        return noticeService.updateIsseenFalse(noticeid);
    }
    @QueryMapping
    public Notice find_notice_by_userid_type_subject(@Argument("userid") String userid, @Argument("typee") int typee, @Argument("subject") int subject){
        return noticeService.getNoticeByUseridandTypeandSubject(userid,typee,subject);
    }

    @SubscriptionMapping
    public Publisher<List<Notice>> sub_all_notice_by_userid(@Argument("userid") String userid){
        return noticeService.subnoticeByUserid(userid);
    }
}
