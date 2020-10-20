package com.sxy.shiro;

import com.sxy.common.lang.Mark;
import com.sxy.entity.po.User;
import com.sxy.service.UserService;
import com.sxy.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBRealm extends AuthorizingRealm {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 权限校验
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 身份校验
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        long id = Long.parseLong(jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject());
        User user = userService.getById(id);

        // 判断账户是否存在
        if (user == null) {
            throw new UnknownAccountException("账户不存在");
        }

        // 判断账户是否冻结
        if (user.getMark().equals(Mark.FROZEN)) {
            throw new LockedAccountException("账户已被冻结");
        }

        // 打包返回账户信息
        Account account = new Account();
        BeanUtils.copyProperties(user, account);
        return new SimpleAuthenticationInfo(account, jwtToken.getCredentials(), getName());
    }
}
