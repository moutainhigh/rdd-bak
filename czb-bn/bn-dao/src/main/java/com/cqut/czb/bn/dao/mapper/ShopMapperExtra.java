package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.Shop;

import java.util.List;

public interface ShopMapperExtra {
    ShopDTO selectShop(ShopDTO shopDTO);

    int updateShopInfo(ShopDTO shopDTO);

    List<ShopDTO> selectSrc(ShopDTO shopDTO);
}
