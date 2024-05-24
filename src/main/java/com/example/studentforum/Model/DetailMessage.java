package com.example.studentforum.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "DetailMessage")
public class DetailMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Detailmessageid")
    private int detailmessageid;

    @ManyToOne
    @JoinColumn(name = "detailmessage_message", referencedColumnName = "messageid")
    private Message detailmessage_message;

    @ManyToOne
    @JoinColumn(name = "user_detailmessage", referencedColumnName = "userid")
    private User user_detailmessage;

    @Column(name = "Isblock")
    private int isblock;

    @Column(name = "Lastseen")
    private LocalDateTime lastseen;

}
