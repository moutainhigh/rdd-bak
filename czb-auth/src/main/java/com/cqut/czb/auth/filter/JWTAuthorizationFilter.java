package com.cqut.czb.auth.filter;

import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.jwt.JwtTool;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.auth.util.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 该类用于对要求安全认证的接口进行身份认证
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private RedisUtils redisUtils;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    //身份认证函数
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        //获取token值
        String tokenHeader = request.getHeader(AuthConfig.TOKEN_HEADER);
        if(redisUtils == null){
            redisUtils = SpringUtils.getBean(RedisUtils.class);
        }

        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            String token = tokenHeader.replace(AuthConfig.TOKEN_PREFIX, "");
            String username = JwtTool.getUsername(token);
            if(redisUtils.get(username + AuthConfig.TOKEN).equals(tokenHeader)) {
                // 验证token信息正确性，并设置到SecurityContextHolder认证信息中
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
                //验证SecurityContextHolder认证信息，有当前请求的信息认证则通过
                super.doFilterInternal(request, response, chain);
            } else {
                chain.doFilter(request, response);
            }
        }catch (Exception e){
            chain.doFilter(request, response);
        }
    }

    // 这里从token中获取用户信息，返回认证信息
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(AuthConfig.TOKEN_PREFIX, "");
        String username = JwtTool.getUsername(token);
        if (username != null){
            return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
        }
        return null;
    }
}

