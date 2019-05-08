package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolRecharge.PetrolRechargeInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolManagement.IPetrolManagementService;
import com.cqut.czb.bn.service.petrolRecharge.IPetrolRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/petrolSaleInfo")
public class PetrolSaleInfoController {
    @Autowired
    IPetrolManagementService petrolManagementService;

    @Autowired
    IPetrolRechargeService petrolRechargeService;
    @RequestMapping(value = "/getSaleInfoList",method = RequestMethod.GET)
    public JSONResult getSaleInfoList(GetPetrolSaleInfoInputDTO inputDTO){
        return new JSONResult(petrolManagementService.getPetrolSaleInfoList(inputDTO));
    }

    @RequestMapping(value = "/getPetrolRechargeList",method = RequestMethod.GET)
    public JSONResult getPetrolRechargeList(PetrolRechargeInputDTO inputDTO){

        return new JSONResult(petrolRechargeService.getPetrolRechargeList(inputDTO));
    }
}
