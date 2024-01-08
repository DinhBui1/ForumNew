package com.example.studentforum.Service;

import com.example.studentforum.Model.Notice;
import com.example.studentforum.Model.User;
import com.example.studentforum.Repository.NoticeRepository;
import com.example.studentforum.Repository.UserRepository;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private UserRepository userRepository;

    public Notice createNotice(String userid, String content, int type, int subject){
        Notice n =new Notice();
        User u = userRepository.getUserById(userid);
        if(u==null){
            return null;
        }
        n.setSubjectid(subject);
        n.setType(type);
        n.setContent(content);
        n.setUser_notice(u);
        n.setCreateday(LocalDateTime.now());
        n.setIsseen(0);
        noticeRepository.save(n);
        return n;

    }

    public String deleteNotice(int noticeid){
        Notice n = noticeRepository.findNoticeByNoiticeid(noticeid);
        if(n==null){
            return "Notice Not Exit";
        }
        noticeRepository.delete(n);
        return "Delete Notice Success";
    }

    public List<Notice> getallNoticeByUserid(String userid){
        User u = userRepository.getUserById(userid);
        if(u==null){
            return null;
        }
        List<Notice> notices = noticeRepository.findNoticeByUserId(userid);
        return  notices;
    }

    public Notice updateIsseenTrue(int noticeid){
        Notice n =noticeRepository.findNoticeByNoiticeid(noticeid);
        n.setIsseen(1);
        noticeRepository.updateisseen(n);

        return n;
    }
    public Notice updateIsseenFalse(int noticeid){
        Notice n =noticeRepository.findNoticeByNoiticeid(noticeid);
        n.setIsseen(0);
        noticeRepository.updateisseen(n);
        return n;
    }
    public Publisher<List<Notice>> subnoticeByUserid(String userid) {
        return subscriber -> Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            List<Notice> notice =  noticeRepository.findNoticeByUserId(userid);
            subscriber.onNext(notice);

        }, 0, 1, TimeUnit.SECONDS);
    }

    public Notice getNoticeByUseridandTypeandSubject(String userid, int type, int subject){
        Notice notices = noticeRepository.findNoticeByUserIdandTypeandSubject(userid,type,subject);
        return notices;
    }
}
