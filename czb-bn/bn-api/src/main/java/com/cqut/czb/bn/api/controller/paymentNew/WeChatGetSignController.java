package com.cqut.czb.bn.api.controller.paymentNew;

import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import static com.cqut.czb.bn.entity.dto.PayConfig.WeChatHttpUtil.httpsRequest;

@RestController
@RequestMapping("/WeChatGetSignController")
public class WeChatGetSignController {

    @ResponseBody
    @RequestMapping(value = "/getSign", method = RequestMethod.GET)
    public String getSign(@RequestParam String code){
        //获取用户的openid
        System.out.println("code" + code);
        String appid= WCProgramConfig.app_id;//这里填写自己的appid和secret
        String secret=WCProgramConfig.app_secret;//这里填写自己的appid和secret
//        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=authorization_code";
        System.out.println("openid" + requestUrl);
        String Result = httpsRequest(requestUrl, "GET", null);
        JSONObject jsonObject = JSONObject.fromObject(Result);
        System.out.println("theOpenid" + jsonObject.get("openid").toString());
        return jsonObject.get("openid").toString();
    }
}
