package com.cqut.czb.bn.api.controller.directChargingSystem;


import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.until.WXSign;
import com.cqut.czb.bn.entity.dto.weChatAppletPushNotification.sendNotification;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static com.cqut.czb.bn.entity.dto.until.WXSign.httpRequest;

@RestController
@RequestMapping("/AliPayReturn")
public class AliPayReturnController {
    @Autowired
    PrepaidRefillService prepaidRefillService;

    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 支付宝购买
     * @param request
     * @param response
     */
    @RequestMapping(value="/aliPayReturn", method= RequestMethod.POST)
    public synchronized void aliPayReturn(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付宝回调——直冲");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.setContentType("text/xml");
        try {
            response.getWriter().print(oilCardRechargeService.aliPayReturn(request,"Direct"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 水电费充值
     * @param request
     * @param response
     */
    @RequestMapping(value="/aliPayElectricityReturn", method= RequestMethod.POST)
    public synchronized void aliPayElectricityReturn(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付宝回调——水电费充值");
        response.setCharacterEncoding("utf-8");
        response.setHeader("content-type", "text/html;charset=utf-8");
        response.setContentType("text/xml");
        try {
            response.getWriter().print(oilCardRechargeService.aliElectricityReturn(request,"Electricity"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getGoodsPrice")
    public JSONResult getGoodsPrice(Integer type) {
        return new JSONResult(prepaidRefillService.getGoodsPrice(type));
    }

    /**
     * 获取微信签名
     * @return
     */
    @PostMapping("/sign")
    @ResponseBody
    public Map<String, String> WapSignSignatureAction(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
//        HttpSession session = request.getSession();
        String url = request.getParameter("url");
//        System.out.println("session："+session);
//        String accesstoken = (String) session.getAttribute(WCProgramConfig.ren_duo_duo_app_id + "accesstoken_session");
        String accesstoken = (String) redisUtils.get("accesstoken");
        System.out.println("accesstoken："+accesstoken);
        if (accesstoken == null || "".equals(accesstoken)) {
            accesstoken = sendNotification.getWXAccessToken();
//            session.setAttribute(WCProgramConfig.ren_duo_duo_app_id + "accesstoken_session", accesstoken);
//            session.setMaxInactiveInterval(7200);
            redisUtils.putAccessToken("accesstoken",accesstoken);
        }
        Map<String, String> js_data = getJSSignMapResult(WCProgramConfig.ren_duo_duo_app_id,accesstoken,url, request);
        System.out.println(3+js_data.toString());
        return js_data;
    }


    public Map<String, String> getJSSignMapResult(String appid, String access_token, String url, HttpServletRequest request){
        Map<String, String> ret = new HashMap<String, String>();
//        String jsapi_ticket=(String)request.getSession().getAttribute(appid+"jsapi_ticket_session");
        String jsapi_ticket = (String) redisUtils.get("jsapi_ticket");
        if(jsapi_ticket==null || "".equals(jsapi_ticket)){
            JSONObject json=httpRequest("https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token+"&type=jsapi", "GET", "");
            System.out.println("jsapi_ticket返回值");
            System.out.println(json);
            jsapi_ticket=(String)json.get("ticket");
            System.out.println(2+jsapi_ticket);
//            request.getSession().setAttribute(appid+"jsapi_ticket_session", jsapi_ticket);
            redisUtils.putAccessToken("jsapi_ticket",jsapi_ticket);
        }
        ret = WXSign.sign(jsapi_ticket, url);
        return ret;
    }
}
