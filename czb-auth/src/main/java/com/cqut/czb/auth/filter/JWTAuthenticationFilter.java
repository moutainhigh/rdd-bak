package com.cqut.czb.auth.filter;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.jwt.JwtTool;
import com.cqut.czb.auth.jwt.JwtUser;
import com.cqut.czb.auth.serviceImpl.AuthUserServiceImpl;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.auth.util.SpringUtils;
import com.cqut.czb.bn.entity.dto.user.LoginUser;
import com.cqut.czb.bn.entity.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 该类用于登录，登录成功后设置token信息
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //设置登录接口的api
        super.setFilterProcessesUrl("/auth/login");
    }

    //登录方法
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        LoginUser loginUser = new LoginUser();
        loginUser.setAccount(request.getParameter("account"));
        loginUser.setPassword(request.getParameter("password"));
        String tokenHeader = request.getHeader(AuthConfig.TOKEN_HEADER);
        if((null == loginUser.getAccount() || "".equals(loginUser.getAccount())) && (null != tokenHeader && tokenHeader.startsWith(AuthConfig.TOKEN_PREFIX))) {
            if(userDetailsService == null){
                userDetailsService = SpringUtils.getBean(AuthUserServiceImpl.class);
            }
            String token = tokenHeader.replace(AuthConfig.TOKEN_PREFIX, "");
            loginUser.setAccount(JwtTool.getUsername(token));
            UserDetails userDetails =  userDetailsService.loadUserByUsername(loginUser.getAccount());
            loginUser.setPassword(userDetails.getPassword());
        }
        if(loginUser.getAccount() == null || loginUser.getAccount() == "") {
            try {
                loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if("" == loginUser.getPassword()) {
            loginUser.setPassword(null);
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getAccount(), loginUser.getPassword(), new ArrayList<>())
        );
    }

    //登录成功后设置token
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        // 调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是JwtUser啦
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        String token = JwtTool.createToken(jwtUser.getAccount(), false);

        if(redisUtils == null){
            redisUtils = SpringUtils.getBean(RedisUtils.class);
        }

        User user=jwtUser.getUser();
//        redisUtil.put(AuthConfig.TOKEN_PREFIX + token, user);
        redisUtils.put(jwtUser.getAccount(), user);
        if(redisUtils.hasKey(jwtUser.getAccount()+AuthConfig.TOKEN)) {
            redisUtils.remove(jwtUser.getAccount()+AuthConfig.TOKEN);
        }
        redisUtils.put(jwtUser.getAccount()+AuthConfig.TOKEN, AuthConfig.TOKEN_PREFIX + token);

        // 返回创建成功的token
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        JSONObject result = new JSONObject();
        result.put(AuthConfig.TOKEN, AuthConfig.TOKEN_PREFIX + token);
        result.put(AuthConfig.STATUS, true);
        response.getWriter().write(result.toJSONString());
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        JSONObject result = new JSONObject();
        result.put(AuthConfig.FAILED_REASON, failed.getMessage());
        result.put(AuthConfig.STATUS, false);
        response.getWriter().write(result.toJSONString());
    }
}
