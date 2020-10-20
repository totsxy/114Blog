package com.sxy.common.handler;

import com.sxy.common.lang.Result;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理Runtime异常
     * 有关逻辑的异常将在这里被捕获
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Result handle(RuntimeException e) {
        return Result.failure(500, e);
    }

    /**
     * 处理Shiro异常
     * 有关认证的异常将在这里被捕获
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle(ShiroException e) {
        String m = e instanceof UnauthenticatedException ? "权限认证失败" : e.getMessage();
        return Result.failure(401, m);
    }

    /**
     * 处理Shiro异常
     * 认证过期时异常将在这里被捕获
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredCredentialsException.class)
    public Result handle(ExpiredCredentialsException e) {
        return Result.failure(401, e);
    }

    /**
     * 处理Hibernate异常
     * 用户提交的异常将在这里被捕获
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handle(MethodArgumentNotValidException e) {
        return Result.failure(
                400,
                e.getBindingResult().getAllErrors()
                        .stream().findFirst().get().getDefaultMessage());
    }

    /**
     * 处理Assert异常
     * 有关断言的异常将在这里被捕获
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handle(IllegalArgumentException e) {
        return Result.failure(400, e);
    }
}
