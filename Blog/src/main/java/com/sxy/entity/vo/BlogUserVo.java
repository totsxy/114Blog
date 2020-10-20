package com.sxy.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogUserVo {

    private Long id;

    private Long uid;

    private String owner;

    private String title;

    private String cover;

    private String summary;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created;
}
