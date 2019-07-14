package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.infoSpread.PartnerDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.PartnerConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partnerConsumption")
public class PartnerConsumptionController {
    @Autowired
    PartnerConsumptionService partnerConsumptionService;

    @GetMapping("/getConsumptionList")
    public JSONResult getConsumptionList(PartnerDTO partnerDTO, PageDTO pageDTO){
        return new JSONResult(partnerConsumptionService.getConsumptionList(partnerDTO,pageDTO));
    }

    @GetMapping("/getDetailConsumption")
    public JSONResult getDetailConsumption(PartnerDTO partnerDTO,PageDTO pageDTO){
        return new JSONResult(partnerConsumptionService.getDetailConsumption(partnerDTO,pageDTO));
    }
}
