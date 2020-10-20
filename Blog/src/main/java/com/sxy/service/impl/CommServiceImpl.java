package com.sxy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxy.entity.po.Comm;
import com.sxy.entity.vo.CommUserVo;
import com.sxy.mapper.CommMapper;
import com.sxy.service.CommService;
import org.springframework.stereotype.Service;

@Service
public class CommServiceImpl extends ServiceImpl<CommMapper, Comm> implements CommService {

    public void deletedByBlogId(Long bid) {
        this.baseMapper.deletedByBlogId(bid);
    }

    public IPage<CommUserVo> allowedCommsByBlogId(Page<CommUserVo> pagination, Long bid) {
        return this.baseMapper.allowedCommsByBlogId(pagination, bid);
    }
}
