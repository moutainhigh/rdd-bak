package com.cqut.czb.bn.api.controller.autoRecharge;

import com.cqut.czb.auth.interceptor.PermissionCheck;
import com.cqut.czb.auth.util.RedisUtils;
import com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult.*;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdInfo;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.AutoRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/autoRecharge")
public class AutoRechargeController {

    @Autowired
    AutoRechargeService autoRechargeService;

    @Autowired
    RedisUtils redisUtils;
    /**
     * 获取 中石油 登录 验证码
     * @return
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public JSONResult getCode(Principal principal){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(autoRechargeService.getCode(user.getUserId()));
    }

    /**
     * 获取 中石油 登录
     * @return
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONResult login(Principal principal, LoginInput loginInput){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(autoRechargeService.login(loginInput, user.getUserId()));
    }

    /**
     * 获取 主卡信息
     * @return
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/readCard", method = RequestMethod.GET)
    public JSONResult getCode(Principal principal, ReadCardInput readCardInput){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(autoRechargeService.readCard(readCardInput, user.getUserId()));
    }

    /**
     * 充值
     * @return
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public JSONResult recharge(Principal principal, RechargeInput rechargeInput){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(autoRechargeService.recharge(rechargeInput, user.getUserId()));
    }

    /**
     * 获取单个油卡的ID
     * @return
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/searchCardId", method = RequestMethod.GET)
    public JSONResult searchCardId(Principal principal, SearchCardInput searchCardInput){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(autoRechargeService.searchCardId(searchCardInput, user.getUserId()));
    }

    /**
     * 获取多个油卡的ID
     * @return
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/searchCardIds", method = RequestMethod.GET)
    public JSONResult searchCardIds(Principal principal, SearchCardInput searchCardInput){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(autoRechargeService.searchCardIds(searchCardInput, user.getUserId()));
    }

    /**
     * 获取模板信息
     * @return
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getTemplateData", method = RequestMethod.GET)
    public JSONResult getTemplateData(Principal principal, TemplateInput templateInput){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(autoRechargeService.getTemplateData(templateInput, user.getUserId()));
    }

    /**
     * 保存模板
     * @return
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/saveTemplate", method = RequestMethod.GET)
    public JSONResult saveTemplate(Principal principal, SaveTemplateInput saveTemplateInput){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(autoRechargeService.saveTemplate(saveTemplateInput, user.getUserId()));
    }

    /**
     * 获取待充值记录
     * @return
     */
    @PermissionCheck(role = "管理员")
    @RequestMapping(value = "/getRechargeList", method = RequestMethod.GET)
    public JSONResult getRechargeList(Principal principal, PetrolRechargeInputDTO petrolRechargeInputDTO){
        User user = (User)redisUtils.get(principal.getName());
        return new JSONResult(autoRechargeService.getRechargeList(petrolRechargeInputDTO, user.getUserId()));
    }
}
