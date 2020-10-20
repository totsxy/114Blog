package com.sxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxy.entity.po.Comm;
import com.sxy.entity.vo.CommUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CommMapper extends BaseMapper<Comm> {

    @Update("update comm set status = 'deleted' where bid = ${bid}")
    void deletedByBlogId(@Param("bid") Long bid);

    @Select("select c.id,u.id as uid,u.name as owner,c.content,c.created from comm c left join user u on c.uid = u.id where c.bid = ${bid} and c.status = 'allowed' order by c.created")
    IPage<CommUserVo> allowedCommsByBlogId(Page<CommUserVo> pagination, @Param("bid") Long bid);
}
