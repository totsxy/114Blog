package com.sxy.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private static String SUCC = "请求成功";
    private static String FAIL = "未知错误";

    private int    code;    // 响应状态
    private String desc;    // 状态说明
    private Object data;    // 响应数据

    private Result() {
        // 覆盖默认构造器限制外部实例化
    }

    private static Result create(int code, String desc, Object data) {
        Result r = new Result();
        r.code = code;
        r.desc = desc;
        r.data = data;
        return r;
    }

    public static Result success(String desc, Object data) {
        return create(200, desc, data);
    }

    public static Result success(String desc) {
        return success(desc, null);
    }

    public static Result success(Object data) {
        return success(SUCC, data);
    }

    public static Result failure(int code, String desc) {
        // 此处进行非空判断
        // 用以提供必要的错误信息
        return create(
                code,
                "".equals(desc) ? FAIL : desc,
                null
        );
    }

    public static Result failure(int code, Exception e) {
        return failure(code, e.getMessage());
    }
}
