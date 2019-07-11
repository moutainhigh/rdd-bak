package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.CommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.service.AppShopSettleInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppShopSettleInServiceImpl implements AppShopSettleInService {

    @Autowired
    private CommodityMapperExtra commodityMapperExtra;

    @Override
    public List<CommodityDTO> selectCommodity(String classification) {
        return commodityMapperExtra.selectCommoditys(classification);
    }
}
