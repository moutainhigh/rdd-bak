package com.cqut.czb.bn.api.controller.weChatAppletPushNotification;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.weChatAppletPushNotification.v2.WxgzhUtil;
import com.cqut.czb.bn.entity.entity.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.cqut.czb.bn.entity.dto.PayConfig.WeChatHttpUtil.httpsRequest;

/**
 * 主要是用来获取用户的openid,以及触发模板消息
 */
@RestController
@RequestMapping("/api/wxController")
public class WxController {

    @Autowired
    RedisUtils redisUtils;

    @ResponseBody
    @RequestMapping("/getUserInfo")
    public String getUserInfo(@RequestParam String code){
        //获取用户的openid
        String appid="wxd2d0171429ac208f";//这里填写自己的appid和secret
        String secret="f3bf5f0e40c5a76eb9e84c9f683bcf1c";//这里填写自己的appid和secret
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        System.out.println(requestUrl);
        String Result = httpsRequest(requestUrl, "GET", null);
        JSONObject jsonObject = JSONObject.fromObject(Result);
        System.out.println(jsonObject);
        return jsonObject.toString();
    }


    @RequestMapping(value = "/sendMessage" ,method = RequestMethod.POST)
    public String sendMessage(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        WxgzhUtil.sendMessageUtils(user.getUserAccount());
        return "ok";
    }
}