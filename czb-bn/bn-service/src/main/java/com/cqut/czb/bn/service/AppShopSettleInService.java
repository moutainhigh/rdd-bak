package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.Commodity.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AppShopSettleInService {
    /**
     * get the corresponding  goods information
     */
    PageInfo<CommodityDTO> selectCommodity(PageDTO pageDTO, String classification);

    /**
     * get all the goods information
     */
    List<AllCommodityDTO> selectAllCommodity(String classification);

    /**
     * get Service details information
     */
    ServiceDetailsDTO selectServiceDetails(String commodityId);

    /**
     * get the nav
     */
    List<NavDTO> selectShopSettleInNav();

    /**
     * get the InputItem
     */
    List<CommodityUserInfoCollectionDTO> getInputItem(String commodityId);
}
