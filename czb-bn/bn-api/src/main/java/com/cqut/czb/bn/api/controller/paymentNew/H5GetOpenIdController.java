package com.cqut.czb.bn.api.controller.paymentNew;

import com.cqut.czb.bn.service.impl.payBack.petrolCoupons.luPay.util.HttpRequest;
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
        System.out.println("code" + code);
        String appid="wx0a4273c49edc6e4a";//这里填写自己的appid和secret
        String secret="5ba2f570dc17eea98c226d600ffce055";//这里填写自己的appid和secret
//        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code";
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        System.out.println("openid" + requestUrl);
        String Result = httpsRequest(requestUrl, "GET", null);
        JSONObject jsonObject = JSONObject.fromObject(Result);
        System.out.println("theOpenid" + jsonObject.get("openid").toString());
        return jsonObject.get("openid").toString();
    }


    @RequestMapping(value = "/getTickets", method = RequestMethod.GET)
    public String getTickets(){
        String appid="wx0a4273c49edc6e4a";//这里填写自己的appid和secret
        String secret="5ba2f570dc17eea98c226d600ffce055";//这里填写自己的appid和secret
        String url = " https://api.weixin.qq.com/cgi-bin/token";

        String params = "grant_type=client_credential&appid=" + appid +
                "&secret=" + secret;
        System.out.println("1:"+params);
        String sr= HttpRequest.httpRequestGet(url, params);
        System.out.println(sr);
        net.sf.json.JSONObject jsonObject= JSONObject.fromObject(sr);

        String access_token = jsonObject.get("access_token").toString();

        https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
        url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
        params = "access_token=" + access_token +
                "&type=jsapi";

        System.out.println("2:"+params);
        sr= HttpRequest.httpRequestGet(url, params);
        System.out.println(sr);
        jsonObject= JSONObject.fromObject(sr);
        String tickets = jsonObject.get("ticket").toString();
        System.out.println("ticket = " + tickets);
        return tickets;

    }
}
