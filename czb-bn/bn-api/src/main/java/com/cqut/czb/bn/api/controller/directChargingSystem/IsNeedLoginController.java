package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.WCProgramConfig;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.dto.until.WXSign;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cqut.czb.bn.entity.dto.weChatAppletPushNotification.sendNotification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static com.cqut.czb.bn.entity.dto.until.WXSign.httpRequest;

@RestController
@RequestMapping("/isNeed")
public class IsNeedLoginController {
    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 是否需要登录
     * @return
     */
    @GetMapping("/isNeedLogin")
    @ResponseBody
    public JSONResult isNeedLogin(){
        return oilCardRechargeService.isNeedLogin();
    }
}


