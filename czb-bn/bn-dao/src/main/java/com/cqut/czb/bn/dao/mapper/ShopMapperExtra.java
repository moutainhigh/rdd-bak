package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.food.OrderPage.OrderPageDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.SettlementDTO;
import com.cqut.czb.bn.entity.dto.shopManagement.ShopManagementDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopMapperExtra {
    ShopDTO selectShop(ShopDTO shopDTO);

    int updateShopInfo(ShopDTO shopDTO);

    List<ShopDTO> selectSrc(ShopDTO shopDTO);

    List<ShopManagementDTO> selectShopManageDTO(ShopManagementDTO shopManagementDTO);

    List<SettlementDTO> selectSettlementDTO(SettlementDTO settlementDTO);

    int updateSettlementOrder(SettlementDTO settlementDTO);

    int insert(ShopDTO shopDTO);

    int updateShopAudit(ShopManagementDTO shopManagementDTO);

    OrderPageDTO selectOrderShopInfo(@Param("shopId") String shopId);

    List<String> selectImg(@Param("shopId") String shopId);
}
