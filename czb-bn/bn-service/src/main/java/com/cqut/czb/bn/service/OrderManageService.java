package com.cqut.czb.bn.service;

import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.order.OrderDTO;
import com.cqut.czb.bn.entity.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

@Service
public interface OrderManageService {
    PageInfo<OrderDTO> getOrderList(OrderDTO orderDTO, PageDTO pageDTO);

    Boolean updateOrderState(OrderDTO orderDTO);

    PageInfo<OrderDTO> getOrderByShop(OrderDTO orderDTO,PageDTO pageDTO,User user);

    PageInfo<OrderDTO> getConsumptionOfService(OrderDTO orderDTO,PageDTO pageDTO,User user);
}
