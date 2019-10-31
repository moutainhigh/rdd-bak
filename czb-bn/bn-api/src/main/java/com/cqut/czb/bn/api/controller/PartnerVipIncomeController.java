package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.partnerVipIncome.PartnerVipIncomeDTO;
import com.cqut.czb.bn.entity.entity.PartnerVipIncome;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PartnerVipIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partnerVipIncome")
public class PartnerVipIncomeController {

    @Autowired
    PartnerVipIncomeService partnerVipIncomeService;

    @GetMapping("/getVipIncomeList")
    public JSONResult getVipIncomeList(PartnerVipIncomeDTO partnerVipIncomeDTO, PageDTO pageDTO){
        return new JSONResult(partnerVipIncomeService.getVipIncomeList(partnerVipIncomeDTO,pageDTO));
    }
    @PostMapping("/settleVipIncome")
    public JSONResult settleVipIncome(PartnerVipIncomeDTO partnerVipIncomeDTO){
        if (partnerVipIncomeService.settleVipIncome(partnerVipIncomeDTO)){
            return new JSONResult("结算成功",200);
        } else {
            return new JSONResult("结算失败",500);
        }
    }
}
