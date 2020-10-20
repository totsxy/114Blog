package com.sxy.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    @TableId(
            value = "id",
            type = IdType.AUTO
    )
    private Long id;

    private String name;

    private String pass;

    private String mail;

    private String role;

    private String mark;

    private LocalDateTime created;

    private LocalDateTime visited;
}
