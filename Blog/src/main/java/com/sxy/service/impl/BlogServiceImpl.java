package com.sxy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxy.entity.po.Blog;
import com.sxy.entity.vo.BlogUserVo;
import com.sxy.mapper.BlogMapper;
import com.sxy.service.BlogService;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Override
    public IPage<BlogUserVo> allowedBlogs(Page<BlogUserVo> pagination) {
        return this.baseMapper.allowedBlogs(pagination);
    }
}
