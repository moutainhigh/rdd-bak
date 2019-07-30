package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.SettlementDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.ShopManagementDTO;

import java.util.List;

public interface ShopMapperExtra {
    ShopDTO selectShop(ShopDTO shopDTO);

    int updateShopInfo(ShopDTO shopDTO);

    List<ShopDTO> selectSrc(ShopDTO shopDTO);

    List<ShopManagementDTO> selectShopManageDTO(ShopManagementDTO shopManagementDTO);

    List<SettlementDTO> selectSettlementDTO(SettlementDTO settlementDTO);

    int updateSettlementOrder(SettlementDTO settlementDTO);
}
