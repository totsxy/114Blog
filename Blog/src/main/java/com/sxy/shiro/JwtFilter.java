package com.sxy.shiro;

import cn.hutool.json.JSONUtil;
import com.sxy.common.lang.Result;
import com.sxy.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtils jwtUtils;

    /**
     * 提取Jwt(Json Web Token)创建Token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        String jwt = jwtUtils.parseJwt(servletRequest);
        return StringUtils.isEmpty(jwt) ? null : new JwtToken(jwt);
    }

    /**
     * Jwt校检登录
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String jwt = jwtUtils.parseJwt(servletRequest);

        // 无Jwt直接放行
        if (StringUtils.isEmpty(jwt)) {
            return true;
        }

        // 校检
        Claims claim = jwtUtils.getClaimByToken(jwt);
        if (claim == null || jwtUtils.isTokenExpired(claim.getExpiration())) {
            // 返回异常信息
            HttpServletResponse httpServletResponse = WebUtils.toHttp(servletResponse);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.getWriter().print(JSONUtil.toJsonStr(Result.failure(401, "token已失效")));
            return false;
        }

        // 登录
        return executeLogin(servletRequest, servletResponse);
    }

    /**
     * 登录成功
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        return super.onLoginSuccess(token, subject, request, response);
    }

    /**
     * 登录失败
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        try {
            // 返回异常信息
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("text/json;charset=utf-8");

            httpServletResponse.getWriter().print(JSONUtil.toJsonStr(Result.failure(401, e)));
        } catch (IOException ignored) {
        }
        return false;
    }

    /**
     * 提供跨域支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));

        // 为跨域预检时发送的OPTIONS请求返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(200);
            return false;
        }

        return super.preHandle(request, response);
    }
}
