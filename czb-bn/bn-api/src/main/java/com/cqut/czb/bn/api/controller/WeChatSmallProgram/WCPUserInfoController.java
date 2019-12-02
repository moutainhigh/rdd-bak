package com.cqut.czb.bn.api.controller.WeChatSmallProgram;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WCPUserInfoService;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-28 20:07
 */
@RestController
@RequestMapping("/api/WCPUserInfo")
public class WCPUserInfoController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    WCPUserInfoService wcpUserInfoService;

    @GetMapping("/getWCPUserCode")
    public JSONResult getWCPUserCode(Principal principal){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpUserInfoService.getRecommendQRCode(user));
    }

    @GetMapping("/getTabBarInfo")
    public JSONResult getTabBarInfo(Principal principal){
        UserDTO user = (UserDTO) redisUtils.get(principal.getName());
        return new JSONResult(wcpUserInfoService.getTabBarInfo(user.getUserId()));
    }
}