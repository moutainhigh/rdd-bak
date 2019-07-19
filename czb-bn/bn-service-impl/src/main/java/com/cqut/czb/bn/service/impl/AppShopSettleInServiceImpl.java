package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.*;
import com.cqut.czb.bn.entity.dto.Commodity.*;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.entity.CommodityUsageRecord;
import com.cqut.czb.bn.entity.entity.Order;
import com.cqut.czb.bn.service.AppShopSettleInService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppShopSettleInServiceImpl implements AppShopSettleInService {

    @Autowired
    public CommodityMapperExtra commodityMapperExtra;

    @Autowired
    public AppRouterMapperExtra appRouterMapperExtra;

    @Autowired
    public CommodityUserInfoCollectionMapperExtra commodityUserInfoCollectionMapperExtra;

    @Autowired
    public CommodityUsageRecordMapper commodityUsageRecordMapper;

    @Autowired
    public OrderMapper orderMapper;

    @Autowired
    public CommodityUsageRecordMapperExtra commodityUsageRecordMapperExtra;


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
    public Boolean useService(String orderId) {
        CommodityUsageRecord commodityUsageRecord = new CommodityUsageRecord();
        commodityUsageRecord.setRecordId(StringUtil.createId());
        commodityUsageRecord.setOrderId(orderId);
        commodityUsageRecord.setUsageTime(new Date());
        commodityUsageRecord.setCreateAt(new Date());
        commodityUsageRecord.setUpdateAt(new Date());
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if(order.getTotalCount() >= commodityUsageRecordMapperExtra.selectOrderIdCount(orderId)){
            order.setState(1);
            orderMapper.updateByPrimaryKeySelective(order);
            return false;
        }else{
            return commodityUsageRecordMapper.insert(commodityUsageRecord) > 0;
        }

    }

    @Override
    public List<Date> getUsageList(String orderId) {
        return commodityUsageRecordMapperExtra.getUsageList(orderId);
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
