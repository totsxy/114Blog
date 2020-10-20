package com.sxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxy.entity.po.Star;
import com.sxy.mapper.StarMapper;
import com.sxy.service.StarService;
import org.springframework.stereotype.Service;

@Service
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements StarService {

}
