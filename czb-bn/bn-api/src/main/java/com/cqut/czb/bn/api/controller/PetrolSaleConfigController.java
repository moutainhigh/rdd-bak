package com.cqut.czb.bn.api.controller;

import com.cqut.czb.bn.entity.dto.petrolSaleConfig.SetAreaConfigInputDTO;
import com.cqut.czb.bn.entity.entity.PetrolSaleConfig;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolSaleConfigService.IPetrolSaleConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/petrolSaleConfig")
public class PetrolSaleConfigController {
    @Autowired
    IPetrolSaleConfigService petrolSaleConfigService;

    /**
     * 获取区域油卡销售配置
     * @return
     */
    @RequestMapping(value = "/getConfigs",method = RequestMethod.GET)
    public JSONResult getSaleInfoList(@RequestParam(name = "area") String area){
        return new JSONResult(petrolSaleConfigService.getPetrolSaleConfigs(area));
    }

    @RequestMapping(value = "/updateSaleConfig",method = RequestMethod.POST)
    public JSONResult updateSaleConfig(@RequestBody PetrolSaleConfig petrolSaleConfig){
        return new JSONResult(petrolSaleConfigService.updatePetrolSaleConfig(petrolSaleConfig));
    }


    @RequestMapping(value = "/setAreaConfig",method = RequestMethod.POST)
    public JSONResult setAreaConfig(@RequestBody SetAreaConfigInputDTO inputDTO){
        return new JSONResult(petrolSaleConfigService.setAreaConfig(inputDTO) );
    }

}
