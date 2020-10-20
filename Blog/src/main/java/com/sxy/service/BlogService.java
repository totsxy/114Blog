package com.sxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxy.entity.po.Blog;
import com.sxy.entity.vo.BlogUserVo;

public interface BlogService extends IService<Blog> {

    IPage<BlogUserVo> allowedBlogs(Page<BlogUserVo> pagination);
}
