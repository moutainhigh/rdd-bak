package com.cqut.czb.bn.api.controller;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.ChargeBackDTO;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetChargeOrderInputDTO;
import com.cqut.czb.bn.entity.dto.ThirdBusiness.GetUnChargeOrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.ThirdBusinessService.GetUnChargeOrderService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/Rdd")
public class ThirdBusinessController {

    @Autowired
    GetUnChargeOrderService getUnChargeOrderService;

    @Autowired
    RedisUtils redisUtils;

    /**
     * 返回未充值的订单
     */
    @RequestMapping(value="/GetUnChargeOrders", method= RequestMethod.GET)
    public synchronized JSONResult RddGetUnChargeOrders(Principal principal) {
        if(principal==null){
            return new JSONResult(405,"未登录");
        }
        User user = (User)redisUtils.get(principal.getName());
        if(!"RddRechargeNum".equals(user.getUserAccount())){
            return new JSONResult(405,"此账户没有权限");
        }
      return new JSONResult(getUnChargeOrderService.getUnChargeOrder());
    }

    /**
     * 返回充值订单信息
     */
    @RequestMapping(value="/InputChargeOrders", method= RequestMethod.POST)
    public synchronized JSONResult RddInputChargeOrders(Principal principal,@RequestBody List<GetChargeOrderInputDTO> list) {
        if(principal==null){
            return new JSONResult(405,"未登录");
        }
        User user = (User)redisUtils.get(principal.getName());
        if(!"RddRechargeNum".equals(user.getUserAccount())){
            return new JSONResult(405,"此账户没有权限");
        }

        if(list==null||list.size()==0){
            return new JSONResult(400,"数据为空");
        }

        List<String> list1=getUnChargeOrderService.InputChargeOrders(list);

        if(list1==null||list1.size()==0){
            return new JSONResult(200,null);
        }else {
            return new JSONResult("订单id有误",400,list1);
        }
    }

}
