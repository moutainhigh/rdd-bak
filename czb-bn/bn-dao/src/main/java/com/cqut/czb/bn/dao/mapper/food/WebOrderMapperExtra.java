package com.cqut.czb.bn.dao.mapper.food;

import com.cqut.czb.bn.entity.dto.ManageFood.ManageOrder.FoodOrder;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface WebOrderMapperExtra {
    Page<FoodOrder> search(FoodOrder foodOrder);

    int sureOrder(@Param("orderId") String orderId);

    int cancelOrder(@Param("orderId") String orderId);
}
