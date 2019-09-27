package com.cqut.czb.bn.service.impl.food.WebOrder;

import com.cqut.czb.bn.dao.mapper.food.DishSystemMapperExtra;
import com.cqut.czb.bn.dao.mapper.food.WebOrderMapperExtra;
import com.cqut.czb.bn.entity.dto.ManageFood.ManageOrder.FoodAllInfo;
import com.cqut.czb.bn.entity.dto.ManageFood.ManageOrder.FoodOrder;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.food.WebOrderService.WebOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebOrderServiceImpl implements WebOrderService{
    @Autowired
    WebOrderMapperExtra mapperExtra;

    @Autowired
    DishSystemMapperExtra dishSystemMapperExtra;


    @Override
    public JSONResult search(FoodOrder foodOrder, PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize());
        Page<FoodOrder> foodOrders = mapperExtra.search(foodOrder);

        return new JSONResult("查询数据成功", 200, new PageInfo(foodOrders));
    }

    @Override
    public JSONResult sureOrder(String orderId) {
        if(mapperExtra.sureOrder(orderId) > 0) {
            return new JSONResult("完成订单成功", 200);
        } else {
            return new JSONResult("完成订单失败", 500);
        }
    }

    @Override
    public JSONResult cancelOrder(String orderId) {
        if(mapperExtra.cancelOrder(orderId) > 0) {
            return new JSONResult("取消订单成功", 200);
        } else {
            return new JSONResult("取消订单失败", 500);
        }
    }
}
