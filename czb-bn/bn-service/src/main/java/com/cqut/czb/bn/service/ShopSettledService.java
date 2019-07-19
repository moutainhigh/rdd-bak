package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface ShopSettledService {
    ShopDTO getShopInfo(ShopDTO shopDTO, User user);

    Boolean updateShopInfo(ShopDTO shopDTO);

    PageInfo<CommodityDTO> getCommodity(CommodityDTO commdityDTO, PageDTO pageDTO, User user);
}
