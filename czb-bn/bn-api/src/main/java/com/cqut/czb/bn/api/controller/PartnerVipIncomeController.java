package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerVipIncomeDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerVipMoney;
import com.cqut.czb.bn.entity.entity.PartnerVipIncome;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PartnerVipIncomeService;
import com.cqut.czb.bn.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/partnerVipIncome")
public class PartnerVipIncomeController {

    @Autowired
    PartnerVipIncomeService partnerVipIncomeService;
    @Autowired
    RedisUtil redisUtil;

    @GetMapping("getVipIncomeList")
    public JSONResult getVipIncomeList(PartnerVipIncomeDTO partnerVipIncomeDTO, PageDTO pageDTO){
        return new JSONResult(partnerVipIncomeService.getVipIncomeList(partnerVipIncomeDTO,pageDTO));
    }

    @GetMapping("getVipIncomeByUser")
    public JSONResult getVipIncomeByUser(Principal principal){
        if (principal == null || principal.getName() == null){
            return new JSONResult("token为空",500);
        }
        User user = (User)redisUtil.get(principal.getName());
        return new JSONResult(partnerVipIncomeService.getVipIncomeByUser(user));
    }

    @PostMapping("settleVipIncome")
    public JSONResult settleVipIncome(@RequestBody PartnerVipIncomeDTO partnerVipIncomeDTO){
        if (partnerVipIncomeService.settleVipIncome(partnerVipIncomeDTO)){
            return new JSONResult("结算成功",200);
        } else {
            return new JSONResult("结算失败",500);
        }
    }

    @GetMapping("initData")
    public JSONResult initData(){
        return new JSONResult(partnerVipIncomeService.initVipIncomeData());
    }

    @PostMapping("add")
    public JSONResult add(@RequestBody PartnerVipMoney partnerVipMoney){
        return new JSONResult(partnerVipIncomeService.addVipIncome(partnerVipMoney.getPartnerId(),partnerVipMoney.getVipConsumption(),partnerVipMoney.getPartnerType()));
    }

}
