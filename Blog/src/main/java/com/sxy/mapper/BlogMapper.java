package com.sxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxy.entity.po.Blog;
import com.sxy.entity.vo.BlogUserVo;
import org.apache.ibatis.annotations.Select;

public interface BlogMapper extends BaseMapper<Blog> {

    @Select("select b.id,u.id as uid,u.name as owner,b.title,b.cover,b.summary,b.content,b.created from blog b left join user u on b.uid = u.id where b.status = 'allowed' order by b.created")
    IPage<BlogUserVo> allowedBlogs(Page<BlogUserVo> pagination);
}
