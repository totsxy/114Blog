package com.sxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxy.entity.po.User;
import com.sxy.mapper.UserMapper;
import com.sxy.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
