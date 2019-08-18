package com.cqut.czb.bn.api.controller.appPayment;

import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.AliPetrolBackInfoDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.PetrolInputDTO;
import com.cqut.czb.bn.entity.dto.appBuyPetrol.WeChatPetrolBackInfoDTO;
import com.cqut.czb.bn.entity.entity.*;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.entity.global.PetrolCache;
import com.cqut.czb.bn.service.AppBuyPetrolService;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * 创建人：陈德强
 * 创建时间：2019/4/23
 * 作用：购买油卡
 */
@RestController
@RequestMapping("/api/AppBuyPetrol")
public class AppBuyPetrolController {

    @Autowired
    private  AppBuyPetrolService appBuyPetrolService;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/buyPetrol",method = RequestMethod.POST)
    public synchronized JSONResult buyPetrol(Principal principal,@RequestBody PetrolInputDTO petrolInputDTO){
//    public JSONResult buyPetrol(PetrolInputDTO petrolInputDTO){
        User user = (User)redisUtils.get(principal.getName());
//        User user=new User();
//        user.setUserAccount("15870596710");
//        user.setUserId("155887754610894");
        //防止数据为空
        if(petrolInputDTO==null||user==null){
            return new JSONResult("申请数据有误", ResponseCodeConstants.FAILURE);
        }
        if (user.getUserAccount().equals("15870596710") || user.getUserAccount().equals("15520024205")) {
            petrolInputDTO.setArea("重庆市");
        }
        petrolInputDTO.setUserAccount(user.getUserAccount());
        petrolInputDTO.setOwnerId(user.getUserId());
        petrolInputDTO.setPaymentMethod(1);//0 佣金购买，1 支付宝，2 微信，3 自己开发的方案，4 合同打款
        petrolInputDTO.setIsVip(user.getIsVip());
        //检测是否有未完成的订单(若存在则将油卡放回，无则继续操作)
        PetrolCache.isContainsNotPay(user.getUserId());
        //处理购油或充值
        Map<String,Object> BuyPetrol=appBuyPetrolService.PurchaseControl(petrolInputDTO);
        if(BuyPetrol==null){
            return new JSONResult("无法生成订单",ResponseCodeConstants.FAILURE);
        }else {
           if(BuyPetrol.get("-1")!=null){
               System.out.println((String)BuyPetrol.get("-1"));
               return  new JSONResult( (String)BuyPetrol.get("-1"),ResponseCodeConstants.FAILURE);
           }else if(BuyPetrol.get("0")!=null){
               System.out.println((AliPetrolBackInfoDTO)BuyPetrol.get("0"));
               return  new JSONResult("购买成功",200,BuyPetrol.get("0"));
           }else if(BuyPetrol.get("2")!=null){
               System.out.println((AliPetrolBackInfoDTO)BuyPetrol.get("2"));
               return  new JSONResult("充值成功",200,BuyPetrol.get("2"));
           }else {
               System.out.println((String)BuyPetrol.get("-1"));
               return new JSONResult("无法生成订单",ResponseCodeConstants.FAILURE);
           }
        }
    }

    /**
     * 微信支付接口
     */
    @RequestMapping(value = "/weChatToPayment", method = RequestMethod.POST)
    public synchronized JSONResult weChatToPayment(Principal principal,@RequestBody PetrolInputDTO petrolInputDTO) {
        User user = (User)redisUtils.get(principal.getName());
        //防止数据为空
        if(petrolInputDTO==null||user==null){
            return new JSONResult("申请数据有误", ResponseCodeConstants.FAILURE);
        }
        if (user.getUserAccount().equals("15870596710") || user.getUserAccount().equals("15520024205")) {
            petrolInputDTO.setArea("重庆市");
        }
        petrolInputDTO.setUserAccount(user.getUserAccount());
        petrolInputDTO.setOwnerId(user.getUserId());
        petrolInputDTO.setPaymentMethod(2);//0 佣金购买，1 支付宝，2 微信，3 自己开发的方案，4 合同打款
        petrolInputDTO.setIsVip(user.getIsVip());
        //检测是否有未完成的订单(若存在则将油卡放回，无则继续操作)
        PetrolCache.isContainsNotPay(user.getUserId());
        //处理购油或充值
        Map<String,Object> BuyPetrol=appBuyPetrolService.PurchaseControl(petrolInputDTO);
        if(BuyPetrol==null){
            return new JSONResult("无法生成订单",ResponseCodeConstants.FAILURE);
        }else {
            if(BuyPetrol.get("-1")!=null){
                System.out.println((String)BuyPetrol.get("-1"));
                return  new JSONResult( (String)BuyPetrol.get("-1"),ResponseCodeConstants.FAILURE);
            }else if(BuyPetrol.get("0")!=null){
                System.out.println((WeChatPetrolBackInfoDTO)BuyPetrol.get("0"));
                return  new JSONResult("购买成功",200,BuyPetrol.get("0"));
            }else if(BuyPetrol.get("2")!=null){
                System.out.println((WeChatPetrolBackInfoDTO)BuyPetrol.get("2"));
                return  new JSONResult("充值成功",200,BuyPetrol.get("2"));
            }else {
                System.out.println((String)BuyPetrol.get("-1"));
                return new JSONResult("无法生成订单",ResponseCodeConstants.FAILURE);
            }
        }
    }

}
