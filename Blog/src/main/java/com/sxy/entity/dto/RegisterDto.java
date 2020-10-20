package com.sxy.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RegisterDto implements Serializable {

    @NotBlank(message = "账户不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    private String pass;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式有误")
    private String mail;
}
