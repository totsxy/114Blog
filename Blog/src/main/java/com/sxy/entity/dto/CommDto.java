package com.sxy.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CommDto implements Serializable {

    @NotNull(message = "博客标识不能为空")
    private Long bid;

    @NotBlank(message = "评论内容不能为空")
    private String content;
}
