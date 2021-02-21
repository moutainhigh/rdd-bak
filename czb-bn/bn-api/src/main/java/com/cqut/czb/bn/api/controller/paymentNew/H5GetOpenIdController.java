package com.cqut.czb.bn.api.controller.paymentNew;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import static com.cqut.czb.bn.entity.dto.PayConfig.WeChatHttpUtil.httpsRequest;

@RestController
@RequestMapping("/H5GetOpenIdController")
public class H5GetOpenIdController {

    @ResponseBody
    @RequestMapping(value = "/getUserOpenId", method = RequestMethod.GET)
    public String getUserOpenId(@RequestParam String code){
        //获取用户的openid
        String appid="wx0a4273c49edc6e4a";//这里填写自己的appid和secret
        String secret="5ba2f570dc17eea98c226d600ffce055";//这里填写自己的appid和secret
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        System.out.println("openid" + requestUrl);
        String Result = httpsRequest(requestUrl, "GET", null);
        JSONObject jsonObject = JSONObject.fromObject(Result);
        System.out.println(jsonObject);
        return jsonObject.toString();
    }
}
