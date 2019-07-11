package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;

import java.util.List;

public interface AppShopSettleInService {
    /**
     * 获取对应商品
     */
    List<CommodityDTO> selectCommodity(String classification);
}
