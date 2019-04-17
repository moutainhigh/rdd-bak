package com.cqut.czb.auth.jwt;

import com.cqut.czb.auth.config.AuthConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
/**
 * 该类为封装的jwt-token工具类
 */
public class JwtTool {

    // 创建token
    public static String createToken(String username, boolean isRememberMe) {
        long expiration = isRememberMe ? AuthConfig.EXPIRATION_REMEMBER : AuthConfig.EXPIRATION;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, AuthConfig.SECRET)
                .setIssuer(AuthConfig.ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token){
        return getTokenBody(token).getSubject();
    }

    // 是否已过期
    public static boolean isExpiration(String token){
        return getTokenBody(token).getExpiration().before(new Date());
    }

    private static Claims getTokenBody(String token){
        return Jwts.parser()
                .setSigningKey(AuthConfig.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}

