package com.sxy.shiro;

import lombok.Data;

import java.io.Serializable;

@Data
public class Account implements Serializable {

    private Long id;

    private String user;

    private String role;

    private String mark;

    private String mail;
}
