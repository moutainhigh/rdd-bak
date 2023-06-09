package com.cqut.czb.bn.api.controller.directChargingSystem;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.directChargingSystem.DirectChargingOrderDto;
import com.cqut.czb.bn.entity.dto.directChargingSystem.OilCardBinging;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.directChargingSystem.OilCardRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/oilCardRecharge")
public class DirectRechargeController {
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
    public JSONResult getInfoNum(Principal principal, DirectChargingOrderDto directChargingOrderDto){

//        if (directChargingOrderDto.getIsNeedLogin() == 1) {
        String userId = ((User)redisUtils.get(principal.getName())).getUserId();
        return new JSONResult(oilCardRechargeService.getOrderInfoList(userId, directChargingOrderDto.getRecordType()));
//        } else {
//            userId = directChargingOrderDto.getUserId();
//            return new JSONResult(oilCardRechargeService.getOnceOrderInfoList(userId, directChargingOrderDto.getRecordType()));
//        }
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
     * 获取所有订单
     * @return
     */
    @PostMapping ("/getAllOnceOrderInfoList")
    public JSONResult getAllOnceOrderInfoList(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.getAllOnceOrderInfoList(directChargingOrderDto));
    }

    /**
     * 统计
     * @return
     */
    @PostMapping ("/getTheStatics")
    public JSONResult getTheStatics(DirectChargingOrderDto directChargingOrderDto){
        return oilCardRechargeService.getTheStatics(directChargingOrderDto);
    }


    /**
     * 获取直充总消费
     * @return
     */
    @GetMapping(value = "/getTotalConsumption")
    public JSONResult getTotalConsumption(int type) {
        return oilCardRechargeService.getTotalConsumption(type);
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
     *
     * @return
     */
    @PostMapping("/updateOrderState")
    @ResponseBody
    public JSONResult updateOrderState(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.updateOrderState(directChargingOrderDto));
    }

    /**
     * 油卡充值状态
     * @return
     */
    @PostMapping("/getOilOrderState")
    @ResponseBody
    public JSONResult getOilOrderState(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.getOilOrderState(directChargingOrderDto));
    }

    /**
     * 话费充值状态
     * @return
     */
    @PostMapping("/getPhoneOrderState")
    @ResponseBody
    public JSONResult getPhoneOrderState(DirectChargingOrderDto directChargingOrderDto){
        return new JSONResult(oilCardRechargeService.getPhoneOrderState(directChargingOrderDto));
    }

    /**
     * 是否可以充值
     * @return
     */
    @PostMapping("/isPhoneRecharge")
    @ResponseBody
    public JSONResult isPhoneRecharge(DirectChargingOrderDto directChargingOrderDto){
        return oilCardRechargeService.isPhoneRecharge(directChargingOrderDto);
    }

    /**
     * 是否需要登录
     * @return
     */
    @GetMapping("/isNeedLogin")
    @ResponseBody
    public JSONResult isNeedLogin(){
        return oilCardRechargeService.isNeedLogin();
    }

    /**
     * 话费重新提交
     * @return
     */
    @PostMapping("/phoneRechargeSubmission")
    @ResponseBody
    public JSONResult phoneRechargeSubmission(DirectChargingOrderDto directChargingOrderDto){
        oilCardRechargeService.phoneRechargeSubmission(directChargingOrderDto);
        return new JSONResult("重新提交成功", 200);
    }

    /**
     * 油卡重新提交
     * @return
     */
    @PostMapping("/onlineorderSubmission")
    @ResponseBody
    public JSONResult onlineorderSubmission(DirectChargingOrderDto directChargingOrderDto){
        oilCardRechargeService.onlineorderSubmission(directChargingOrderDto);
        return new JSONResult("重新提交成功", 200);
    }
}
