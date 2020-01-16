package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.ShopMapperExtra;
import com.cqut.czb.bn.dao.mapper.UserMapperExtra;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.WCPCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityComdirmOrderDTO;
import com.cqut.czb.bn.entity.dto.user.UserDTO;
import com.cqut.czb.bn.service.WCPCommodityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
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

    @Autowired
    UserMapperExtra userMapperExtra;

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
//        if(weChatCommodityComdirmOrderDTO.getTime() != null){
//            Long interval =  (System.currentTimeMillis() - weChatCommodityComdirmOrderDTO.getTime().getTime()) / (1000 * 60);
//            if(interval <= 10){
        UserDTO userDTO = userMapperExtra.findUserDTOById(userId);
        weChatCommodityComdirmOrderDTO.setUserId(userDTO.getBindingid());
//        weChatCommodityComdirmOrderDTO.setShopId(shopMapperExtra.selectShopIdByUserId(userDTO.getBindingid()));
        return weChatCommodityOrderMapperExtra.updateCommodityOrderState(weChatCommodityComdirmOrderDTO) > 0;
//            }
//            return false;
//        }
//        return false;
    }

    @Override
    public WCPCommodityOrderDTO getOneCommodityOrderByShop(String userId, String orderId) {
        UserDTO userDTO = userMapperExtra.findUserDTOById(userId);
        return weChatCommodityOrderMapperExtra.selectOneCommodityOrderByShopUserId(userDTO.getBindingid(), orderId);
    }

    @Override
    public List<WCPCommodityOrderDTO> getAllCommodityOrderByLeader(String userId, Integer orderState,Integer page) {
        UserDTO userDTO = userMapperExtra.findUserDTOById(userId);
        Integer pageSize1 = 0;
        Integer pageSize2 = 1000;
        if(page != null){
            pageSize1 = 10*(page-1);
            pageSize2 = 10*page;
        }
        return weChatCommodityOrderMapperExtra.selectAllCommodityOrderByLeaderId(userDTO.getBindingid(), orderState,pageSize1,pageSize2);
    }

    @Override
    public Double getTotalPrice(String userId, Integer orderState) {
        UserDTO userDTO = userMapperExtra.findUserDTOById(userId);
        return weChatCommodityOrderMapperExtra.selectTotalPrice(userDTO.getBindingid(), orderState);
    }

}