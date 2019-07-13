package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.AppRouterMapperExtra;
import com.cqut.czb.bn.dao.mapper.CommodityMapperExtra;
import com.cqut.czb.bn.entity.dto.Commodity.AllCommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.CommodityDTO;
import com.cqut.czb.bn.entity.dto.Commodity.NavDTO;
import com.cqut.czb.bn.entity.dto.Commodity.ServiceDetailsDTO;
import com.cqut.czb.bn.service.AppShopSettleInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppShopSettleInServiceImpl implements AppShopSettleInService {

    @Autowired
    public CommodityMapperExtra commodityMapperExtra;

    @Autowired
    public AppRouterMapperExtra appRouterMapperExtra;

    @Override
    public List<CommodityDTO> selectCommodity(String classification) {
        return commodityMapperExtra.selectCommoditys(classification);
    }

    @Override
    public List<AllCommodityDTO> selectAllCommodity(String classification) {
        return commodityMapperExtra.selectAllCommodity(classification);
    }

    @Override
    public List<NavDTO> selectShopSettleInNav() {
        List<NavDTO> navDTOS=commodityMapperExtra.selectShopSettleNav();
        if(navDTOS!=null){
            NavDTO navDTO=new NavDTO();
            navDTO.setNavName("全部");
            navDTOS.add(0,navDTO);
        }
        return navDTOS;
    }

    @Override
    public ServiceDetailsDTO selectServiceDetails(String commodityId) {
        return commodityMapperExtra.selectServiceDetails(commodityId);
    }
}
