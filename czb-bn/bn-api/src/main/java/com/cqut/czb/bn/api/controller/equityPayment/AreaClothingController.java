package com.cqut.czb.bn.api.controller.equityPayment;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.equityPayment.EquityPaymentAreaClothingDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.equityPaymentService.AreaClothingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/equityPaymentAreaClothing")
public class AreaClothingController {

    AreaClothingService areaClothingService;

    /**
     * 获取区服信息列表
     * @return
     */
    @RequestMapping(value = "/getAreaClothingList", method = RequestMethod.GET)
    public JSONResult getCommodityList(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO, PageDTO pageDTO) {
        return new JSONResult(areaClothingService.getAreaClothingList(equityPaymentAreaClothingDTO, pageDTO));
    }

    /**
     * 新增区服信息列表
     * @return
     */
    @RequestMapping(value = "/insertAreaClothing", method = RequestMethod.POST)
    public JSONResult insertAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO) {
        return new JSONResult(areaClothingService.insertAreaClothing(equityPaymentAreaClothingDTO));
    }

    /**
     * 更新区服信息列表
     * @return
     */
    @RequestMapping(value = "/updateAreaClothing", method = RequestMethod.POST)
    public JSONResult updateAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO) {
        return new JSONResult(areaClothingService.updateAreaClothing(equityPaymentAreaClothingDTO));
    }

    /**
     * 删除区服信息列表
     * @return
     */
    @RequestMapping(value = "/deleteAreaClothing", method = RequestMethod.POST)
    public JSONResult deleteAreaClothing(EquityPaymentAreaClothingDTO equityPaymentAreaClothingDTO) {
        return new JSONResult(areaClothingService.deleteAreaClothing(equityPaymentAreaClothingDTO));
    }
}
