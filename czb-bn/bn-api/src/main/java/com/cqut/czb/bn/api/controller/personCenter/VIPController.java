package com.cqut.czb.bn.api.controller.personCenter;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.personCenterService.IVIPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * 作者：曹渝
 * 模块：app个人中心，升级vip
 * 创建时间: 2019/05/01
 */
@RestController
@RequestMapping("/api/personCenter/VIP")
public class VIPController {

    @Autowired
    IVIPService vipService;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/createVIPOrder",method = RequestMethod.POST)
    public JSONResult createVIPOrder(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(vipService.createVIPOrder(user));
    }

    @RequestMapping(value = "/purchaseVIP",method = RequestMethod.POST)
    public void purchaseVIP(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        try {
                response.getWriter().print(vipService.purchaseVIP(request));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
