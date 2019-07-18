package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.AppRouterMapperExtra;
import com.cqut.czb.bn.dao.mapper.CommodityMapperExtra;
import com.cqut.czb.bn.dao.mapper.CommodityUserInfoCollectionMapperExtra;
import com.cqut.czb.bn.entity.dto.Commodity.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.service.AppShopSettleInService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppShopSettleInServiceImpl implements AppShopSettleInService {

    @Autowired
    public CommodityMapperExtra commodityMapperExtra;

    @Autowired
    public AppRouterMapperExtra appRouterMapperExtra;

    @Autowired
    public CommodityUserInfoCollectionMapperExtra commodityUserInfoCollectionMapperExtra;


    @Override
    public List<CommodityDTO> selectCommodity(PageDTO pageDTO, String classification) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<CommodityDTO> commodityDTOList=commodityMapperExtra.selectCommoditys(classification);
        PageInfo<CommodityDTO> pageInfo=new PageInfo<>(commodityDTOList);
        return pageInfo.getList();
    }


    public List<UserCommodityOrderDTO> getCommodityOrderList(String userId,Integer state) {
        return commodityMapperExtra.getCommodityOrderList(userId,state);
    }

    @Override
    public Boolean useService(Integer orderId) {

        return null;
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
    public List<CommodityUserInfoCollectionDTO> getInputItem(String commodityId) {
        if(commodityId==null&&commodityId.equals("")){
            return null;
        }
        return commodityUserInfoCollectionMapperExtra.selectInfoInput(commodityId);
    }

    @Override
    public ServiceDetailsDTO selectServiceDetails(String commodityId) {
        return commodityMapperExtra.selectServiceDetails(commodityId);
    }
}
