package com.sxy.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Blog implements Serializable {

    @TableId(
            value = "id",
            type = IdType.AUTO
    )
    private Long id;

    private Long uid;

    @NotBlank(message = "博客标题不能为空")
    private String title;

    private String cover;

    @NotBlank(message = "博客概要不能为空")
    private String summary;

    @NotBlank(message = "博客内容不能为空")
    private String content;

    private LocalDateTime created;

    private String status;
}
