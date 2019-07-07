package com.cqut.czb.auth.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.config.AuthConfig;
import com.cqut.czb.auth.jwt.JwtTool;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.auth.util.SpringUtils;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.impl.Cache.RolePermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Service
public class PrivilegeInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(Class.class);



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        logger.info(requestUri);
        String tokenHeader = request.getHeader(AuthConfig.TOKEN_HEADER);
        String token = "";
        if(tokenHeader == null ){
           return true;
        }
        token = tokenHeader.replace(AuthConfig.TOKEN_PREFIX, "");
        String userAccount = JwtTool.getUsername(token);
        RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);
        UserDTO userDTO = (UserDTO) redisUtils.get(userAccount);
        String roleId = userDTO.getRoleId();
        boolean permited = RolePermissions.checkApi(roleId,requestUri);
        if(!permited){
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            JSONObject result = new JSONObject();
            result.put(AuthConfig.FAILED_REASON, "没有访问权限");
            result.put(AuthConfig.STATUS, false);
            result.put("code",403);
            result.put("message","没有访问权限");
            result.put("data","没有访问权限");
            try {
                response.getWriter().write(result.toJSONString());
                return false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
