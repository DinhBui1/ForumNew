package com.example.studentforum.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ContentMessageDTO {
    private int contentid;
    private String userid;
    private int messageid;
    private Integer parentid;
    private String content;
    private LocalDateTime createday;
    private LocalDateTime updateday;
    private List<TotalIcon> totalicon;
    private String image;
}
