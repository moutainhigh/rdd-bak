package com.cqut.czb.bn.api.controller.autoRecharge;

import com.cqut.czb.bn.entity.dto.AutoRechargeLoginResult.*;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.rentCar.companyContractSigned.ContractIdInfo;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.autoRecharge.AutoRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autoRecharge")
public class AutoRechargeController {

    @Autowired
    AutoRechargeService autoRechargeService;
    /**
     * 获取 中石油 登录 验证码
     * @return
     */
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public JSONResult getCode(){
        return new JSONResult(autoRechargeService.getCode());
    }

    /**
     * 获取 中石油 登录
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONResult login(LoginInput loginInput){
        return new JSONResult(autoRechargeService.login(loginInput));
    }

    /**
     * 获取 主卡信息
     * @return
     */
    @RequestMapping(value = "/readCard", method = RequestMethod.GET)
    public JSONResult getCode(ReadCardInput readCardInput){
        return new JSONResult(autoRechargeService.readCard(readCardInput));
    }

    /**
     * 充值
     * @return
     */
    @RequestMapping(value = "/recharge", method = RequestMethod.GET)
    public JSONResult recharge(RechargeInput rechargeInput){
        return new JSONResult(autoRechargeService.recharge(rechargeInput));
    }

    /**
     * 获取单个油卡的ID
     * @return
     */
    @RequestMapping(value = "/searchCardId", method = RequestMethod.GET)
    public JSONResult searchCardId(SearchCardInput searchCardInput){
        return new JSONResult(autoRechargeService.searchCardId(searchCardInput));
    }

    /**
     * 获取多个油卡的ID
     * @return
     */
    @RequestMapping(value = "/searchCardIds", method = RequestMethod.GET)
    public JSONResult searchCardIds(SearchCardInput searchCardInput){
        return new JSONResult(autoRechargeService.searchCardIds(searchCardInput));
    }

    /**
     * 获取模板信息
     * @return
     */
    @RequestMapping(value = "/getTemplateData", method = RequestMethod.GET)
    public JSONResult getTemplateData(TemplateInput templateInput){
        return new JSONResult(autoRechargeService.getTemplateData(templateInput));
    }

    /**
     * 保存模板
     * @return
     */
    @RequestMapping(value = "/saveTemplate", method = RequestMethod.GET)
    public JSONResult saveTemplate(SaveTemplateInput saveTemplateInput){
        return new JSONResult(autoRechargeService.saveTemplate(saveTemplateInput));
    }

    /**
     * 获取待充值记录
     * @return
     */
    @RequestMapping(value = "/getRechargeList", method = RequestMethod.GET)
    public JSONResult getRechargeList(PetrolRechargeInputDTO petrolRechargeInputDTO){
        return new JSONResult(autoRechargeService.getRechargeList(petrolRechargeInputDTO));
    }
}
