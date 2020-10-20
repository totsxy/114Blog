package com.sxy.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "账户不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String pass;
}
