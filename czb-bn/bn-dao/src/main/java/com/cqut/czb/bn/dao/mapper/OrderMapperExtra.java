package com.cqut.czb.bn.dao.mapper;

import com.cqut.czb.bn.entity.dto.order.OrderDTO;

import java.util.List;

public interface OrderMapperExtra {
    List<OrderDTO> selectOrderList(OrderDTO orderDTO);

    int updateOrderState(OrderDTO orderDTO);

    List<OrderDTO> selectOrderByShop(OrderDTO orderDTO);

    List<OrderDTO> selectRemainService(OrderDTO orderDTO);

    List<OrderDTO> selectConsumptionOfService(OrderDTO  orderDTO);
}
