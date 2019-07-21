package com.cqut.czb.bn.api.controller.appPayment;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.appBuyService.BuyServiceDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.AppBuyServiceService;
import com.cqut.czb.bn.util.RedisUtil;
import com.cqut.czb.bn.util.constants.ResponseCodeConstants;
import net.sf.json.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/AppBuyService")
public class AppBuyServiceController {

    @Autowired
    public AppBuyServiceService appBuyServiceService;

    @Autowired
    public RedisUtils redisUtils;

    /**
     * app buy service and input information(购买服务和录入信息)支付宝
     */
    @RequestMapping(value = "/AliBuyService", method = RequestMethod.POST)
        public JSONResult AliBuyService(Principal principal,@RequestBody BuyServiceDTO buyServiceDTO) {
        User user = (User)redisUtils.get(principal.getName());
        String info =appBuyServiceService.AliPayBuyService(user,buyServiceDTO);
        if(info==null){
            return new JSONResult("无法生成订单", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("购买成功",200,info);
        }
    }

    /**
     * app buy service and input information(购买服务和录入信息)微信
     */
    @RequestMapping(value = "/WeChatBuyService", method = RequestMethod.POST)
    public JSONResult WeChatBuyService(Principal principal,@RequestBody BuyServiceDTO buyServiceDTO) {
        User user = (User)redisUtils.get(principal.getName());
        JSONObject info =appBuyServiceService.WeChatBuyService(user,buyServiceDTO);
        if(info==null){
            return new JSONResult("无法生成订单", ResponseCodeConstants.FAILURE);
        }else {
            return  new JSONResult("购买成功",200,info);
        }
    }
}
