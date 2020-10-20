package com.sxy.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxy.common.lang.Mark;
import com.sxy.common.lang.Result;
import com.sxy.common.lang.Role;
import com.sxy.entity.dto.LoginDto;
import com.sxy.entity.dto.RegisterDto;
import com.sxy.entity.po.User;
import com.sxy.service.UserService;
import com.sxy.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
public class UserController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        // 检查账户是否存在
        User user = userService.getOne(
                new QueryWrapper<User>().eq("name", loginDto.getName())
        );
        Assert.notNull(user, "该用户不存在");

        // 检查密码是否匹配
        Assert.isTrue(
                user.getPass().equals(
                        SecureUtil.md5(loginDto.getPass())
                ),
                "用户名或密码错误"
        );

        // 更新最后登录信息
        userService.updateById(user.setVisited(LocalDateTime.now()));
        jwtUtils.writeJwtToken(response, user.getId());

        return Result.success("登录成功",
                MapUtil.builder()
                        .put("id", user.getId())
                        .put("name", user.getName())
                        .put("mail", user.getMail())
                        .put("role", user.getRole())
                        .put("mark", user.getMark())
                        .map()
        );
    }

    /**
     * 登出接口
     */
    @GetMapping("/logout")
    @RequiresAuthentication
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success("登出成功");
    }

    /**
     * 注册接口
     */
    @PostMapping("/register")
    public Result register(@Validated @RequestBody RegisterDto registerDto) {
        User user = userService.getOne(
                new QueryWrapper<User>().eq("name", registerDto.getName())
        );

        Assert.isTrue(user == null, "该账户已经存在");
        user = new User();

        BeanUtils.copyProperties(registerDto, user);

        // 初始化必要信息并保存
        userService.save(
                user.setPass(SecureUtil.md5(user.getPass()))
                        .setRole(Role.BASIC)
                        .setMark(Mark.UNVERIFIED)
                        .setCreated(LocalDateTime.now())
        );

        return Result.success("注册成功");
    }
}
