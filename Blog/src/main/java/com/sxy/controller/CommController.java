package com.sxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxy.common.lang.Result;
import com.sxy.common.lang.Status;
import com.sxy.entity.dto.CommDto;
import com.sxy.entity.po.Comm;
import com.sxy.service.BlogService;
import com.sxy.service.CommService;
import com.sxy.util.UscUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.sxy.util.Checker.checkBlog;
import static com.sxy.util.Checker.checkComm;

@RestController
public class CommController {

    @Autowired
    BlogService blogService;

    @Autowired
    CommService commService;

    /**
     * 评论列表
     */
    @GetMapping("/blog/{bid}/comments")
    public Result comments(
            @PathVariable(name = "bid") Long bid,
            @RequestParam(defaultValue = "1") Integer current
    ) {
        checkBlog(blogService.getById(bid));
        return Result.success(
                commService.allowedCommsByBlogId(
                        new Page<>(current, 12), bid
                )
        );
    }

    /**
     * 评论添加
     */
    @PostMapping("/comment/create")
    @RequiresAuthentication
    public Result create(@Validated @RequestBody CommDto commDto) {
        checkBlog(blogService.getById(commDto.getBid()));

        Comm comm = new Comm();
        BeanUtils.copyProperties(commDto, comm);

        commService.saveOrUpdate(
                comm
                        .setUid(UscUtils.userId())
                        .setCreated(LocalDateTime.now())
                        .setStatus(Status.ALLOWED)
        );
        return Result.success("评论添加成功");
    }

    /**
     * 评论删除
     */
    @GetMapping("/comment/delete/{id}")
    @RequiresAuthentication
    public Result delete(@PathVariable Long id) {
        Comm comm = checkComm(commService.getById(id));

        Assert.isTrue(
                UscUtils.checkId(comm.getUid()),
                "当前账户无权删除该评论"
        );

        commService.updateById(comm.setStatus(Status.DELETED));
        return Result.success("评论删除成功");
    }
}
