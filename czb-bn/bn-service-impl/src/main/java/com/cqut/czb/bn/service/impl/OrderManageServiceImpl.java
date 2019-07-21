package com.cqut.czb.bn.service.impl;

import com.cqut.czb.bn.dao.mapper.OrderMapperExtra;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.order.OrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.service.OrderManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderManageServiceImpl implements OrderManageService{

    @Autowired
    OrderMapperExtra orderMapperExtra;

    @Override
    public PageInfo<OrderDTO> getOrderList(OrderDTO orderDTO, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(orderMapperExtra.selectOrderList(orderDTO));
    }

    @Override
    public Boolean updateOrderState(OrderDTO orderDTO) {
        return orderMapperExtra.updateOrderState(orderDTO)>0;
    }

    @Override
    public PageInfo<OrderDTO> getOrderByShop(OrderDTO orderDTO, PageDTO pageDTO, User user) {
        if ("".equals(orderDTO.getId())||orderDTO.getId()==null){
            orderDTO.setShopId(user.getUserId());
        }
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        List<OrderDTO> orders = orderMapperExtra.selectOrderByShop(orderDTO);
        if(orders!=null&&orders.size()!=0) {
            for (int i = 0; i < orders.size(); i++) {
                    orders.get(i).setMessages(orders.get(i).getMessage().split(","));
            }
        }
        return new PageInfo<>(orders);
    }

    @Override
    public PageInfo<OrderDTO> getConsumptionOfService(OrderDTO orderDTO, PageDTO pageDTO, User user) {
        if ("".equals(orderDTO.getId())||orderDTO.getId()==null){
            orderDTO.setShopId(user.getUserId());
        }
        PageHelper.startPage(pageDTO.getCurrentPage(),pageDTO.getPageSize());
        return new PageInfo<>(orderMapperExtra.selectConsumptionOfService(orderDTO));
    }
}
