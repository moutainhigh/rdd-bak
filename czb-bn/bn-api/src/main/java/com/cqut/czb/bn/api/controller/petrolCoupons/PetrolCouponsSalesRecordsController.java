package com.cqut.czb.bn.api.controller.petrolCoupons;

import com.cqut.czb.bn.entity.dto.petrolSaleInfo.GetPetrolSaleInfoInputDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.petrolCoupons.PetrolCouponsSalesRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作者： 陈爽
 * 模块：中石化优惠券管理
 * 创建时间： 2020/4/26
 */
@RestController
@RequestMapping("/api/PetrolCoupons")
public class PetrolCouponsSalesRecordsController {

    @Autowired
    PetrolCouponsSalesRecordsService petrolCouponsSalesRecordsService;

    /**
     * 初始化数据，查询
     * @param input
     * @return
     */
    @GetMapping("/selectPetrolCouponsSalesRecords")
    public JSONResult selectVipApply(GetPetrolSaleInfoInputDTO input) {
        return new JSONResult(petrolCouponsSalesRecordsService.selectPetrolCouponsSalesRecords(input));
    }
}
