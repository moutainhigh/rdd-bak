package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.petrolManagement.GetPetrolListInputDTO;
import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolManagement.IPetrolManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/petrolSaleInfo")
public class PetrolSaleInfoController {
    @Autowired
    IPetrolManagementService petrolManagementService;
    @RequestMapping(value = "/getSaleInfoList",method = RequestMethod.GET)
    public JSONResult getSaleInfoList(GetPetrolSaleInfoInputDTO inputDTO){
        return new JSONResult(petrolManagementService.getPetrolSaleInfoList(inputDTO));
    }
}
