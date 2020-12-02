package com.cqut.czb.bn.api.controller.withoutCard;

import com.cqut.czb.bn.entity.dto.withoutCard.CommodityWithoutCardDto;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.withoutCard.WithoutCardCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者： 陈爽
 * 模块： 无卡加油区域管理
 * 创建时间： 2020/11/17
 */

@RestController
@RequestMapping("/api/withoutCardCommodity")
public class WithoutCardCommodityController {

    @Autowired
    WithoutCardCommodityService withoutCardCommodityService;

    @PostMapping("/listPetrolCommodity")
    public JSONResult listPetrolCommodity(CommodityWithoutCardDto commodityWithoutCardDto){
        return new JSONResult(withoutCardCommodityService.listPetrolCommodity(commodityWithoutCardDto));
    }

    @PostMapping("/insetCommodity")
    public JSONResult insetCommodity(CommodityWithoutCardDto commodityWithoutCardDto){
        return withoutCardCommodityService.insetCommodity(commodityWithoutCardDto);
    }

    @PostMapping("removeCommodity")
    public JSONResult removeCommodity(String commodityIds){
        return withoutCardCommodityService.removeCommodity(commodityIds);
    }

    @PostMapping("updateCommodity")
    public JSONResult updateCommodity(CommodityWithoutCardDto commodityWithoutCardDto){
        return withoutCardCommodityService.updateCommodity(commodityWithoutCardDto);
    }

    @PostMapping("getCommodityById")
    public JSONResult getCommodityById(String commodityId){
        return new JSONResult(withoutCardCommodityService.getCommodityById(commodityId));
    }

    @PostMapping("updateCommodityState")
    public JSONResult updateCommodityState(CommodityWithoutCardDto commodityWithoutCardDto){
        return withoutCardCommodityService.updateCommodityState(commodityWithoutCardDto);
    }
}
