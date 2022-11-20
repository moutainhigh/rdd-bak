package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.api.controller.autoRecharge.UserRechargeController;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.PayConfig.UrlConfig;
import com.cqut.czb.bn.entity.dto.dict.DictInputDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.*;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.IDictService;
import com.cqut.czb.bn.service.IRoleService;
import com.cqut.czb.bn.service.autoRecharge.UserRechargeService;
import com.cqut.czb.bn.service.directChargingSystem.DirectChargingCommodityService;
import com.cqut.czb.bn.service.directChargingSystem.NoLoginDirectSystemService;
import com.cqut.czb.bn.service.directChargingSystem.PrepaidRefillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/noLoginDirectSystem")
public class NoLoginDirectSystemController {

    @Autowired
    DirectChargingCommodityService directChargingCommodityService;

    @Autowired
    IDictService dictService;

    @Autowired
    NoLoginDirectSystemService noLoginDirectSystemService;

    @Autowired
    UserRechargeService userRechargeService;

    @GetMapping("/getAllCommodity")
    public JSONResult getAllCommodity(PageDTO pageDTO) {
        return new JSONResult(directChargingCommodityService.getAllCommodity(pageDTO));
    }

    @RequestMapping(value = "/getDicByName",method = RequestMethod.GET)
    public JSONResult getDicByName(DictInputDTO dictInputDTO){
        return new JSONResult(dictService.getDicByName(dictInputDTO));
    }

    @RequestMapping(value = "/rechargeUrl",method = RequestMethod.GET)
    public JSONResult rechargeUrl(DirectChargingOrderDto directChargingOrderDto){
        String id = System.currentTimeMillis() + UUID.randomUUID().toString().substring(8, 13).replace("-", "");
        directChargingOrderDto.setOurOrderId(id);
        String inert = noLoginDirectSystemService.insertPhoneOrder(directChargingOrderDto);
        String theUrl = new String();
        // 支付宝链接
        if (directChargingOrderDto.getPayWay() == 1) {
            theUrl = "http://" + UrlConfig.The_Domain_Name + "/alipayPhoneRecharge?id=" + inert;
        }else if (directChargingOrderDto.getPayWay() == 2) { //微信链接
            theUrl = "http://" + UrlConfig.The_Domain_Name + "/wechatPhoneRecharge?id=" + inert;
        }
        CustomerRechargeDto customerRechargeDto = new CustomerRechargeDto();
        customerRechargeDto.setTheUrl(theUrl);
        customerRechargeDto.setOurOrderId(id);
        return new JSONResult("提交成功", 200, customerRechargeDto);
    }

    @RequestMapping(value = "/getOrderDetails",method = RequestMethod.POST)
    public JSONResult getOrderDetails(CustomerPhoneOrderDto customerPhoneOrderDto){
        return noLoginDirectSystemService.getOrderDetails(customerPhoneOrderDto);
    }

    @PostMapping("/oil")
    public ThirdOrderCallback insertOilOrder(ThirdOrder order){
        try {
            return userRechargeService.thirdInsert(order);
        } catch (Exception e){
            e.printStackTrace();
            return new ThirdOrderCallback(400, "下单失败", order.getOrderId());
        }
    }
}
