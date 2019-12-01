package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityComdirmOrderDTO;
import com.cqut.czb.bn.service.WCPCommodityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @auther nihao
 * @create 2019-11-26 16:32
 */
@Service
public class WCPCommodityOrderServiceImpl implements WCPCommodityOrderService {

    @Autowired
    WeChatCommodityOrderMapperExtra weChatCommodityOrderMapperExtra;

    @Autowired
    ShopMapperExtra shopMapperExtra;

    @Override
    public WCPCommodityOrderDTO getCurrentOrder(String userId) {
        return weChatCommodityOrderMapperExtra.selectCurrentOrder(userId);
    }

    @Override
    public List<WCPCommodityOrderDTO> getAllCommodityOrder(String userId) {
        return weChatCommodityOrderMapperExtra.selectAllCommodityOrder(userId);
    }

    @Override
    public Boolean comfirmDeliveryState(String userId, String orderId) {
        return weChatCommodityOrderMapperExtra.updateCommodityDeliveryState(userId, orderId) > 0;
    }

    @Override
    public WCPCommodityOrderDTO getOneCommodityOrderById(String userId, String orderId) {
        return weChatCommodityOrderMapperExtra.selectOneCommodityOrderById(userId, orderId);
    }

    @Override
    public Boolean comfirmCommodityOrder(String userId, WeChatCommodityComdirmOrderDTO weChatCommodityComdirmOrderDTO) {
        weChatCommodityComdirmOrderDTO.setUserId(userId);
        weChatCommodityComdirmOrderDTO.setShopId(shopMapperExtra.selectShopIdByUserId(userId));
        return weChatCommodityOrderMapperExtra.updateCommodityOrderState(weChatCommodityComdirmOrderDTO) > 0;
    }
}