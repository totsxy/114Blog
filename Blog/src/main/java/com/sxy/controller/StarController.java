package com.sxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sxy.common.lang.Result;
import com.sxy.entity.dto.StarDto;
import com.sxy.entity.po.Star;
import com.sxy.service.BlogService;
import com.sxy.service.StarService;
import com.sxy.util.UscUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static com.sxy.util.Checker.checkBlog;

@RestController
public class StarController {

    @Autowired
    BlogService blogService;

    @Autowired
    StarService starService;

    /**
     * 点赞详情
     */
    @GetMapping("/blog/{bid}/stars")
    public Result stars(@PathVariable Long bid) {
        checkBlog(blogService.getById(bid));

        return Result.success(
                new StarDto()
                        .setBid(bid)
                        .setNum((long) starService.list(
                                new QueryWrapper<Star>().eq("bid", bid)
                                ).size()
                        )
                        .setLiked(
                                UscUtils.account() != null && starService.getOne(
                                        new QueryWrapper<Star>()
                                                .eq("bid", bid)
                                                .eq("uid", UscUtils.userId())
                                ) != null)
        );
    }

    /**
     * 点赞&取消点赞
     */
    @GetMapping("/star/update/{bid}")
    @RequiresAuthentication
    public Result delete(@PathVariable Long bid) {
        checkBlog(blogService.getById(bid));
        String mode;

        Star star = starService.getOne(
                new QueryWrapper<Star>()
                        .eq("bid", bid)
                        .eq("uid", UscUtils.userId())
        );

        if (star == null) {
            starService.save(new Star()
                    .setUid(UscUtils.userId())
                    .setBid(bid)
                    .setCreated(LocalDateTime.now())
            );
            mode = "点赞成功";
        } else {
            starService.removeById(star.getId());
            mode = "取消成功";
        }

        return Result.success(mode);
    }
}
