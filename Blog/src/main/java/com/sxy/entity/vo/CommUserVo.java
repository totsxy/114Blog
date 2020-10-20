package com.sxy.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommUserVo {
    private Long id;

    private Long uid;

    private String owner;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime created;
}
