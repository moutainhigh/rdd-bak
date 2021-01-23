package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.dto.until.WXSign;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cqut.czb.bn.entity.dto.weChatAppletPushNotification.sendNotification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/oilCardRecharge")
public class OilCardRechargeController {
    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 获取当前用户所有订单
     * @param principal
     * @return
     */
    @PostMapping("/getOrderInfoList")
    public JSONResult getInfoNum(Principal principal, Integer type){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(oilCardRechargeService.getOrderInfoList(user.getUserId(), type));
    }

    /**
     * 获取所有订单
     * @return
     */
    @PostMapping ("/getAllOrderInfoList")
    public JSONResult getAllOrderInfoList(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.getAllOrderInfoList(directChargingOrderDto));
    }

    /**
     * 绑定油卡
     * @param principal
     * @param oilCardBinging
     * @return
     */
    @PostMapping("/bindingOilCard")
    public JSONResult bindingOilCard(Principal principal, OilCardBinging oilCardBinging){
        User user = (User)redisUtils.get(principal.getName());
        return oilCardRechargeService.bindingOilCard(user.getUserId(), oilCardBinging);
    }

    @PostMapping("/getAllUserCard")
    public JSONResult getAllUserCard(DirectChargingOrderDto directChargingOrderDto){
        return oilCardRechargeService.getAllUserCard(directChargingOrderDto);
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
        HttpSession session = request.getSession();
        String url = request.getParameter("url");
        String accesstoken = (String) session.getAttribute(WCProgramConfig.ren_duo_duo_app_id + "accesstoken_session");
        if (accesstoken == null || "".equals(accesstoken)) {
            accesstoken = sendNotification.getWXAccessToken();
            request.getSession().setAttribute("accesstoken_session", accesstoken);
            request.getSession().setMaxInactiveInterval(7200);
        }
        Map<String, String> js_data = WXSign.getJSSignMapResult(WCProgramConfig.ren_duo_duo_app_id,accesstoken,url, request);
        return js_data;
    }

}
