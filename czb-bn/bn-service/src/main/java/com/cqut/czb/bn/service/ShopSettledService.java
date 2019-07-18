package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.shop.ShopDTO;
import com.cqut.czb.bn.entity.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopSettledService {
    ShopDTO getShopInfo(ShopDTO shopDTO, User user);
}
