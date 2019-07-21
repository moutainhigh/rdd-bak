package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.entity.Order;

import com.cqut.czb.bn.entity.dto.order.OrderDTO;

import java.util.List;

public interface OrderMapperExtra {

    int insert(Order record);

    int updateByPrimaryKeySelective(Order record);

    List<OrderDTO> selectOrderList(OrderDTO orderDTO);

    int updateOrderState(OrderDTO orderDTO);

    List<OrderDTO> selectOrderByShop(OrderDTO orderDTO);

    List<OrderDTO> selectRemainService(OrderDTO orderDTO);

    List<OrderDTO> selectConsumptionOfService(OrderDTO  orderDTO);
}
