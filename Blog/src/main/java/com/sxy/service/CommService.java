package com.sxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sxy.entity.po.Comm;
import com.sxy.entity.vo.CommUserVo;

public interface CommService extends IService<Comm> {

    void deletedByBlogId(Long bid);

    IPage<CommUserVo> allowedCommsByBlogId(Page<CommUserVo> pagination, Long bid);
}
