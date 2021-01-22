package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.AliPetrolBackInfoDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appRechargeVip.RechargeVipDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.entity.Petrol;
import com.cqut.czb.bn.entity.entity.PetrolSalesRecords;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/DirectChargingPay")
public class DirectChargingPayController {
    @Autowired
    OilCardRechargeService oilCardRechargeService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * app buy service and input information(购买服务和录入信息)支付宝
     */
    @RequestMapping(value = "/AlipayRechargeDirect", method = RequestMethod.POST)
    public JSONResult AlipayRechargeDirect(Principal principal,@RequestBody DirectChargingOrderDto directChargingOrderDto) {
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserId("155892403286206");
//        rechargeVipDTO.setArea("重庆市");
        String info =oilCardRechargeService.AlipayRechargeDirect(directChargingOrderDto);
        if(info==null){
            return new JSONResult("充值失败", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("充值成功",200,info);
        }
    }

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
        try {
            response.getWriter().print(oilCardRechargeService.aliPayReturn(request,"direct"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
