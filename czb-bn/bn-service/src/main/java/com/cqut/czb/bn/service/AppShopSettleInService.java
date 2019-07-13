package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.Commodity.AllCommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.NavDTO;
import com.cqut.czb.bn.entity.dto.Commodity.ServiceDetailsDTO;
import com.cqut.czb.bn.entity.dto.appPersonalCenter.AppRouterDTO;

import java.util.List;

public interface AppShopSettleInService {
    /**
     * get the corresponding  goods information
     */
    List<CommodityDTO> selectCommodity(String classification);

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
}
