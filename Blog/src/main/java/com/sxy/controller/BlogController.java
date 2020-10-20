package com.sxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxy.common.lang.Result;
import com.sxy.common.lang.Status;
import com.sxy.entity.po.Blog;
import com.sxy.entity.po.Star;
import com.sxy.service.BlogService;
import com.sxy.service.CommService;
import com.sxy.service.StarService;
import com.sxy.util.UscUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.sxy.util.Checker.checkBlog;

@RestController
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    CommService commService;

    @Autowired
    StarService starService;


    /**
     * 博客列表
     */
    @GetMapping("/blogs")
        public Result blogs(@RequestParam(defaultValue = "1") Integer current) {
        return Result.success(
                blogService.allowedBlogs(
                        new Page<>(current, 12)
                )
        );
    }

    /**
     * 博客详情
     */
    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        return Result.success(
                checkBlog(blogService.getById(id))
        );
    }

    /**
     * 博客删除
     */
    @GetMapping("/blog/delete/{id}")
    @RequiresAuthentication
    public Result delete(@PathVariable Long id) {
        Blog blog = checkBlog(blogService.getById(id));

        Assert.isTrue(
                UscUtils.checkId(blog.getUid()),
                "当前账户无权删除该博客"
        );

        blogService.updateById(blog.setStatus(Status.DELETED));
        commService.deletedByBlogId(id);
        starService.remove(
                new QueryWrapper<Star>().eq("bid", id)
        );

        return Result.success("博客删除成功");
    }

    /**
     * 博客修改
     */
    @PostMapping("/blog/update")
    @RequiresAuthentication
    public Result update(@Validated @RequestBody Blog blog) {
        Blog tmp;
        String mode;

        // 修改博客
        if (blog.getId() != null) {
            tmp = checkBlog(blogService.getById(blog.getId()));
            Assert.isTrue(
                    UscUtils.checkId(blog.getUid()),
                    "当前账户无权编辑该博客"
            );
            mode = "修改";
            // 新建博客
        } else {
            tmp = new Blog()
                    .setUid(UscUtils.userId())
                    .setCreated(LocalDateTime.now())
                    .setStatus(Status.ALLOWED);
            mode = "添加";
        }

        BeanUtils.copyProperties(blog, tmp, "id", "uid", "created", "status");
        blogService.saveOrUpdate(tmp);
        return Result.success(String.format("博客%s成功", mode));
    }
}
