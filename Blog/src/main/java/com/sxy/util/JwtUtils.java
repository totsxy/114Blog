package com.sxy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * jwt工具类
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "sxy.jwt")
public class JwtUtils {

    private String secret;
    private String header;
    private long   expire;

    private String generateToken(long userId) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(String.valueOf(userId))
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public void writeJwtToken(HttpServletResponse response, Long id) {
        String jwt = generateToken(id);

        response.setHeader(header, jwt);
        response.setHeader("Access-Control-Expose-Headers", header);
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    public String parseJwt(ServletRequest request) {
        return WebUtils.toHttp(request).getHeader(header);
    }
}
