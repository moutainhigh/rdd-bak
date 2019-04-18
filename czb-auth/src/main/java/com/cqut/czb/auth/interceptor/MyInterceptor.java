package com.cqut.czb.auth.interceptor;

import com.cqut.czb.auth.redis.RedisUtil;
import com.cqut.czb.bn.util.HttpJiamiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Map<String, String[]> map = request.getParameterMap();
//        Map<String, String> czbMap = new HashMap<>();
//
//        if (map.get("sign") != null && map.get("sign")[0] != null) {
////            map.remove("sign");
//
//            for (String s : map.keySet()) {
//                czbMap.put(s, map.get(s)[0]);
//            }
//            czbMap.remove("sign");
//
//            String requestSigh = map.get("sign")[0];
//            String czbSign = HttpJiamiUtils.CZBSign(czbMap);
//            if (requestSigh.equals(czbSign)) {
//                return true;
//            }
//        }
//
//        return false;
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
