package com.cqut.czb.auth.interceptor;

import com.cqut.czb.bn.util.HttpJiamiUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String[]> map = request.getParameterMap();
        Map<String, String> czbMap = new HashMap<>();

        if (map.get("sign") != null && map.get("sign")[0] != null) {
//            map.remove("sign");

            for (String s : map.keySet()) {
                czbMap.put(s, map.get(s)[0]);
            }
            czbMap.remove("sign");

            String requestSigh = map.get("sign")[0];
            String ddmSign = HttpJiamiUtils.CZBSign(czbMap);
            if (requestSigh.equals(ddmSign)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
