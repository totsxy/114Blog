package com.sxy.util;

import com.sxy.common.lang.Status;
import com.sxy.entity.po.Blog;
import com.sxy.entity.po.Comm;
import org.springframework.util.Assert;

public class Checker {
    public static Blog checkBlog(Blog blog) {
        Assert.notNull(blog, "该博客不存在或已被删除");
        Assert.isTrue(!blog.getStatus().equals(Status.DELETED), "该博客已被删除");
        Assert.isTrue(!blog.getStatus().equals(Status.BLOCKED), "该博客已被封禁");
        return blog;
    }

    public static Comm checkComm(Comm comm) {
        Assert.notNull(comm, "该评论不存在或已被删除");
        Assert.isTrue(!comm.getStatus().equals(Status.DELETED), "该评论已被删除");
        Assert.isTrue(!comm.getStatus().equals(Status.BLOCKED), "该评论已被封禁");
        return comm;
    }
}
