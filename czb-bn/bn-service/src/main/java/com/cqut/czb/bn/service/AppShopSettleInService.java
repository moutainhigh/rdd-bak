package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.Commodity.*;
import com.cqut.czb.bn.entity.dto.PageDTO;

import java.util.Date;
import java.util.List;

public interface AppShopSettleInService {
    /**
     * get the corresponding  goods information
     */
    List<CommodityDTO> selectCommodity(PageDTO pageDTO, String classification);

    /**
     * get all the goods information
     */
    List<AllCommodityDTO> selectAllCommodity(String classification);


    List<UserCommodityOrderDTO> getCommodityOrderList(String userId,Integer state);

    Boolean useService(String orderId);

    List<Date> getUsageList(String orderId);

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
