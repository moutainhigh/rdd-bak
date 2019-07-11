package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;

import java.util.List;

public interface AppShopSettleInService {
    /**
     * 获取对应商品
     */
    List<CommodityDTO> selectCommodity(String classification);

    /**
     * 获取首页的所有路由
     * @param appRouterDTO
     * @return
     */
    List<AppRouterDTO> selectShopSettleInRouters(AppRouterDTO appRouterDTO);
}
