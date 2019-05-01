package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolManagement.IPetrolManagementService;
import com.cqut.czb.bn.service.petrolSaleConfigService.IPetrolSaleConfigService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/petrolSaleConfig")
public class PetrolSaleConfigController {
    @Autowired
    IPetrolSaleConfigService petrolSaleConfigService;

    /**
     * 获取区域油卡销售配置
     * @return
     */
    @RequestMapping(value = "/getConfigs",method = RequestMethod.GET)
    public JSONResult getSaleInfoList(){
        return new JSONResult(petrolSaleConfigService.getPetrolSaleConfigs());
    }

    @RequestMapping(value = "/updateSaleConfig",method = RequestMethod.POST)
    public JSONResult updateSaleConfig(@RequestBody PetrolSaleConfig petrolSaleConfig){
        return new JSONResult(petrolSaleConfigService.updatePetrolSaleConfig(petrolSaleConfig) );
    }


}
